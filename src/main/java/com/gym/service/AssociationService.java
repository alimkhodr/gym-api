package com.gym.service;

import com.gym.exception.ConflictException;
import com.gym.exception.NotFoundException;
import com.gym.model.BaseEntity;
import com.gym.repository.LinkRepository;

import java.util.List;

public class AssociationService<O extends BaseEntity, T extends BaseEntity> {

    private final LinkRepository links;
    private final CrudService<O> owners;
    private final CrudService<T> targets;

    public AssociationService(LinkRepository links, CrudService<O> owners, CrudService<T> targets) {
        this.links = links;
        this.owners = owners;
        this.targets = targets;
    }

    public void link(long ownerId, long targetId) {
        O owner = owners.findById(ownerId);
        targets.findById(targetId);
        List<Long> linked = links.findTargets(ownerId);
        if (linked.contains(targetId)) {
            throw new ConflictException("already linked");
        }
        beforeLink(owner, linked.size());
        links.link(ownerId, targetId);
    }

    public void unlink(long ownerId, long targetId) {
        if (!links.unlink(ownerId, targetId)) {
            throw new NotFoundException("link not found");
        }
    }

    public List<T> findTargets(long ownerId) {
        owners.findById(ownerId);
        return links.findTargets(ownerId).stream().map(targets::findById).toList();
    }

    public List<O> findOwners(long targetId) {
        targets.findById(targetId);
        return links.findOwners(targetId).stream().map(owners::findById).toList();
    }

    protected void beforeLink(O owner, int linkedCount) {
    }
}
