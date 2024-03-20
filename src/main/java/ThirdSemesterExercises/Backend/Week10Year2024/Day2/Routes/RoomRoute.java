package ThirdSemesterExercises.Backend.Week10Year2024.Day2.Routes;

import ThirdSemesterExercises.Backend.Week10Year2024.Day2.Controllers.RoomController;
import ThirdSemesterExercises.Backend.Week10Year2024.Day2.Controllers.SecurityController;
import ThirdSemesterExercises.Backend.Week10Year2024.Day2.DAOs.RoomDAO;
import ThirdSemesterExercises.Backend.Week10Year2024.Day2.Persistence.HibernateConfig;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoute {
    private static RoomDAO roomDAO;

    public RoomRoute(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
    private static SecurityController securityController = new SecurityController(emf);

    public EndpointGroup roomRoutes() {
        return () -> {
            before(securityController.authenticate());
            get("/rooms", RoomController.getAll(roomDAO), Role.ADMIN);
            get("/rooms/{id}", RoomController.getRoom(roomDAO), Role.ADMIN);
            post("/rooms", RoomController.create(roomDAO), Role.ADMIN);
            put("/rooms/{id}", RoomController.update(roomDAO), Role.ADMIN);
            delete("/rooms/{id}", RoomController.delete(roomDAO), Role.ADMIN);
        };
    }
}
