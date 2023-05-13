package org.acme.reactive.crud;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.acme.reactive.crud.pojo.Fruit;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.net.URI;

@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    @ConfigProperty(name = "myapp.schema.create", defaultValue = "true")
    boolean schemaCreate;

    @Inject
    PgPool client;

    @PostConstruct
    void config() {
        if (schemaCreate) {
            initDb();
        }
    }

    private void initDb() {
        client.query("DROP TABLE IF EXISTS fruits")
                .flatMap(r -> client.query("CREATE TABLE fruits (id SERIAL PRIMARY KEY, name TEXT NOT NULL)"))
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Kiwi')"))
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Durian')"))
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Pomelo')"))
                .flatMap(r -> client.query("INSERT INTO fruits (name) VALUES ('Lychee')"))
                .await().indefinitely();
    }

    @GET
    public Multi<Fruit> get() {
        return Fruit.findAll(client);
    }

    @GET
    @Path("{id}")
    public Uni<Response> getSingle(@PathParam Long id) {
        return Fruit.findById(client, id)
                .onItem().apply(fruit -> fruit != null ? Response.ok(fruit) : Response.status(Status.NOT_FOUND))
                .onItem().apply(ResponseBuilder::build);
    }

    @POST
    public Uni<Response> create(Fruit fruit) {
        return fruit.save(client)
                .onItem().apply(id -> URI.create("/fruits/" + id))
                .onItem().apply(uri -> Response.created(uri).build());
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(@PathParam Long id, Fruit fruit) {
        return fruit.update(client)
                .onItem().apply(updated -> updated ? Status.OK : Status.NOT_FOUND)
                .onItem().apply(status -> Response.status(status).build());
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@PathParam Long id) {
        return Fruit.delete(client, id)
                .onItem().apply(deleted -> deleted ? Status.NO_CONTENT : Status.NOT_FOUND)
                .onItem().apply(status -> Response.status(status).build());
    }
}