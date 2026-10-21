package com.gym.http;

import com.gym.exception.MethodNotAllowedException;
import com.gym.model.Equipment;
import com.gym.model.GymClass;
import com.gym.service.AssociationService;
import com.gym.service.EnrollmentService;
import com.gym.service.GymClassService;

public class GymClassHandler extends CrudHandler<GymClass> {

    private final EnrollmentService enrollments;
    private final AssociationService<GymClass, Equipment> equipment;

    public GymClassHandler(GymClassService classes, EnrollmentService enrollments,
                           AssociationService<GymClass, Equipment> equipment) {
        super(classes, GymClass.class);
        this.enrollments = enrollments;
        this.equipment = equipment;
    }

    @Override
    protected Response nested(Request request, long id) {
        return switch (request.segment(1)) {
            case "members" -> association(request, id, enrollments);
            case "equipment" -> association(request, id, equipment);
            default -> super.nested(request, id);
        };
    }

    private Response association(Request request, long classId, AssociationService<GymClass, ?> service) {
        if (request.size() == 2) {
            return get(request, () -> service.findTargets(classId));
        }
        if (request.size() > 3) {
            return super.nested(request, classId);
        }
        long targetId = request.id(2);
        switch (request.method()) {
            case "POST" -> service.link(classId, targetId);
            case "DELETE" -> service.unlink(classId, targetId);
            default -> throw new MethodNotAllowedException(request.method());
        }
        return Response.noContent();
    }
}
