
import io.gatling.javaapi.core.CoreDsl;
import io.gatling.javaapi.core.OpenInjectionStep;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;

public class EmployeSimulation extends Simulation {
    private final int NB_EMPLOYE= 1100; // a modifier en fonction du nombre de cas d'enregistrement
    // Scénario pour récupérer la liste des employés
    ScenarioBuilder scr = CoreDsl.scenario("Test basique")
            .exec(HttpDsl.http("Requête GET simple")
                    .get("http://localhost:8080/api/employe")
                    .check(HttpDsl.status().is(200))
                    .check(CoreDsl.jsonPath("$[*]").count().is(NB_EMPLOYE)))
            .pause(5);

    // Scénario pour creer des employé
    ScenarioBuilder scrCreat = CoreDsl.scenario("Creation d'employe")
            .exec(HttpDsl.http("Requête POST")
                    .post("http://localhost:8080/api/employe")
                    .header("Content-Type", "application/json")
                    .body(CoreDsl.StringBody("{\"nom\": \"Foli\", \"prenom\": \"Komi\", \"salaire\": \"245000\" }"))
                    .check(HttpDsl.status().is(200)))
            .pause(5);


    {
      //  setUp(scrCreat.injectOpen(OpenInjectionStep.atOnceUsers(1000))).protocols(HttpDsl.http.baseUrl("https://localhost"));
        setUp(scr.injectOpen(OpenInjectionStep.atOnceUsers(100))).protocols(HttpDsl.http.baseUrl("https://localhost"));
    }
}
