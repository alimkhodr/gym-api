package com.gym;

import com.gym.db.Database;
import com.gym.http.CrudHandler;
import com.gym.http.GymClassHandler;
import com.gym.http.InstructorHandler;
import com.gym.http.MemberHandler;
import com.gym.http.PlanHandler;
import com.gym.model.Equipment;
import com.gym.model.GymClass;
import com.gym.model.Instructor;
import com.gym.model.Plan;
import com.gym.repository.EquipmentRepository;
import com.gym.repository.GymClassRepository;
import com.gym.repository.InstructorRepository;
import com.gym.repository.LinkRepository;
import com.gym.repository.MemberRepository;
import com.gym.repository.PlanRepository;
import com.gym.service.AssociationService;
import com.gym.service.CrudService;
import com.gym.service.EnrollmentService;
import com.gym.service.GymClassService;
import com.gym.service.MemberService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;

        Database database = new Database("jdbc:h2:./data/gym");
        database.init();

        CrudService<Plan> plans = new CrudService<>(new PlanRepository(database), "plan");
        CrudService<Instructor> instructors = new CrudService<>(new InstructorRepository(database), "instructor");
        CrudService<Equipment> equipment = new CrudService<>(new EquipmentRepository(database), "equipment");
        MemberService members = new MemberService(new MemberRepository(database), plans);
        GymClassService classes = new GymClassService(new GymClassRepository(database), instructors);

        EnrollmentService enrollments = new EnrollmentService(
                new LinkRepository(database, "class_members", "class_id", "member_id"), classes, members);
        AssociationService<GymClass, Equipment> classEquipment = new AssociationService<>(
                new LinkRepository(database, "class_equipment", "class_id", "equipment_id"), classes, equipment);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/plans", new PlanHandler(plans, members));
        server.createContext("/members", new MemberHandler(members, enrollments));
        server.createContext("/instructors", new InstructorHandler(instructors, classes));
        server.createContext("/classes", new GymClassHandler(classes, enrollments, classEquipment));
        server.createContext("/equipment", new CrudHandler<>(equipment, Equipment.class));
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();

        System.out.println("Server running on http://localhost:" + port);
    }
}
