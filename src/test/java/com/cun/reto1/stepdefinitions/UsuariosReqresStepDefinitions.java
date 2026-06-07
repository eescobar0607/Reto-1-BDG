package com.cun.reto1.stepdefinitions;

import com.cun.reto1.context.ApiResponseContext;
import com.cun.reto1.interactions.DeleteUser;
import com.cun.reto1.interactions.GetUsers;
import com.cun.reto1.interactions.RegisterUser;
import com.cun.reto1.interactions.RegisterUserWithoutPassword;
import com.cun.reto1.interactions.UpdateUser;
import com.cun.reto1.questions.JsonFieldValue;
import com.cun.reto1.questions.JsonStringFieldValue;
import com.cun.reto1.questions.ResponseStatusCode;
import com.cun.reto1.questions.UsersCount;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import com.cun.reto1.questions.ResponseBodyIsEmpty;
import com.cun.reto1.interactions.GetSingleUser;


public class UsuariosReqresStepDefinitions {


    private void guardarUltimaRespuesta() {
        Response response = SerenityRest.lastResponse();
        apiResponseContext.setStatusCode(response.statusCode());
        apiResponseContext.setResponseBody(response.asString());
    }


    private final ApiResponseContext apiResponseContext = new ApiResponseContext();
    private Actor actor;


    @Given("que el servicio de usuarios de ReqRes está disponible")
    public void queElServicioDeUsuariosDeReqResEstaDisponible() {
        actor = Actor.named("Analista API");
        actor.attemptsTo(GetUsers.fromPage(2));
        guardarUltimaRespuesta();

        actor.should(
                seeThat("el código de disponibilidad del servicio de usuarios",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(200)),
                seeThat("la respuesta de disponibilidad contiene usuarios",
                        UsersCount.from(apiResponseContext),
                        greaterThan(0))
        );
    }

    @When("el actor consulta la lista de usuarios de la página 2")
    public void elActorConsultaLaListaDeUsuariosDeLaPagina2() {
        actor.attemptsTo(GetUsers.fromPage(2));

        Response response = SerenityRest.lastResponse();
        apiResponseContext.setStatusCode(response.statusCode());
        apiResponseContext.setResponseBody(response.asString());
    }

    @Then("la respuesta debe ser exitosa y contener al menos un usuario")
    public void laRespuestaDebeSerExitosaYContenerAlMenosUnUsuario() {
        actor.should(
                seeThat("el código de respuesta",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(200)),
                seeThat("la cantidad de usuarios en la respuesta",
                        UsersCount.from(apiResponseContext),
                        greaterThan(0))
        );
    }



    @Given("que el servicio de registro de ReqRes está disponible")
    public void queElServicioDeRegistroDeReqResEstaDisponible() {
        actor = Actor.named("Analista API");
        actor.attemptsTo(RegisterUser.withValidCredentials());
        guardarUltimaRespuesta();

        actor.should(
                seeThat("el código de disponibilidad del servicio de registro",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(200)),
                seeThat("la respuesta de disponibilidad del registro contiene id",
                        JsonFieldValue.from(apiResponseContext, "id"),
                        notNullValue()),
                seeThat("la respuesta de disponibilidad del registro contiene token",
                        JsonStringFieldValue.from(apiResponseContext, "token"),
                        not(emptyOrNullString()))
        );
    }


    @When("el actor registra un usuario con correo y contraseña válidos")
    public void elActorRegistraUnUsuarioConCorreoYContrasenaValidos() {
        actor.attemptsTo(RegisterUser.withValidCredentials());

        Response response = SerenityRest.lastResponse();
        apiResponseContext.setStatusCode(response.statusCode());
        apiResponseContext.setResponseBody(response.asString());
    }

    @Then("la respuesta de registro debe ser exitosa y contener id y token")
    public void laRespuestaDeRegistroDebeSerExitosaYContenerIdYToken() {
        actor.should(
                seeThat("el código de respuesta del registro",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(200)),
                seeThat("el id generado en el registro",
                        JsonFieldValue.from(apiResponseContext, "id"),
                        notNullValue()),
                seeThat("el token generado en el registro",
                        JsonStringFieldValue.from(apiResponseContext, "token"),
                        not(emptyOrNullString()))
        );
    }


    @When("el actor intenta registrar un usuario sin contraseña")
    public void elActorIntentaRegistrarUnUsuarioSinContrasena() {
        actor.attemptsTo(RegisterUserWithoutPassword.withMissingPassword());

        Response response = SerenityRest.lastResponse();
        apiResponseContext.setStatusCode(response.statusCode());
        apiResponseContext.setResponseBody(response.asString());
    }

    @Then("la respuesta debe indicar el error Missing password")
    public void laRespuestaDebeIndicarElErrorMissingPassword() {
        actor.should(
                seeThat("el código de respuesta del registro inválido",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(400)),
                seeThat("el mensaje de error del registro inválido",
                        JsonStringFieldValue.from(apiResponseContext, "error"),
                        equalTo("Missing password"))
        );
    }


    @Given("que el servicio de actualización de usuarios de ReqRes está disponible")
    public void queElServicioDeActualizacionDeUsuariosDeReqResEstaDisponible() {
        actor = Actor.named("Analista API");
        actor.attemptsTo(GetSingleUser.withId(2));
        guardarUltimaRespuesta();

        actor.should(
                seeThat("el código de disponibilidad del servicio de actualización",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(200)),
                seeThat("el usuario disponible para actualizar existe",
                        JsonFieldValue.from(apiResponseContext, "data.id"),
                        notNullValue())
        );
    }

    @When("el actor actualiza el nombre y el trabajo del usuario con id 2")
    public void elActorActualizaElNombreYElTrabajoDelUsuarioConId2() {
        actor.attemptsTo(UpdateUser.withIdAndValidData(2));

        Response response = SerenityRest.lastResponse();
        apiResponseContext.setStatusCode(response.statusCode());
        apiResponseContext.setResponseBody(response.asString());
    }

    @Then("la respuesta de actualización debe ser exitosa y contener name, job y updatedAt")
    public void laRespuestaDeActualizacionDebeSerExitosaYContenerNameJobYUpdatedAt() {
        actor.should(
                seeThat("el código de respuesta de actualización",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(200)),
                seeThat("el nombre actualizado",
                        JsonStringFieldValue.from(apiResponseContext, "name"),
                        equalTo("morpheus")),
                seeThat("el trabajo actualizado",
                        JsonStringFieldValue.from(apiResponseContext, "job"),
                        equalTo("zion resident")),
                seeThat("la fecha de actualización",
                        JsonStringFieldValue.from(apiResponseContext, "updatedAt"),
                        not(emptyOrNullString()))
        );
    }

    @Given("que el servicio de eliminación de usuarios de ReqRes está disponible")
    public void queElServicioDeEliminacionDeUsuariosDeReqResEstaDisponible() {
        actor = Actor.named("Analista API");
        actor.attemptsTo(GetSingleUser.withId(2));
        guardarUltimaRespuesta();

        actor.should(
                seeThat("el código de disponibilidad del servicio de eliminación",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(200)),
                seeThat("el usuario disponible para eliminar existe",
                        JsonFieldValue.from(apiResponseContext, "data.id"),
                        notNullValue())
        );
    }

    @When("el actor elimina el usuario con id 2")
    public void elActorEliminaElUsuarioConId2() {
        actor.attemptsTo(DeleteUser.withId(2));

        Response response = SerenityRest.lastResponse();
        apiResponseContext.setStatusCode(response.statusCode());
        apiResponseContext.setResponseBody(response.asString());
    }

    @Then("la respuesta de eliminación debe ser exitosa con código 204")
    public void laRespuestaDeEliminacionDebeSerExitosaConCodigo204() {
        actor.should(
                seeThat("el código de respuesta de eliminación",
                        ResponseStatusCode.from(apiResponseContext),
                        equalTo(204)),
                seeThat("el cuerpo de la respuesta está vacío",
                        ResponseBodyIsEmpty.from(apiResponseContext),
                        equalTo(true))
        );
    }
}