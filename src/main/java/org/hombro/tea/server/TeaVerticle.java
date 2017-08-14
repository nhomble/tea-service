package org.hombro.tea.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.hombro.tea.question.QuestionProvider;
import org.hombro.tea.question.code.CodeAnswerResult;
import org.hombro.tea.question.code.CodingQuestion;
import org.hombro.tea.question.code.Language;

import java.util.List;

/**
 * Created by nicolas on 8/12/2017.
 */
public class TeaVerticle extends AbstractVerticle {
    private final static Logger logger = LoggerFactory.getLogger(TeaVerticle.class);
    private final int port;

    private BodyHandler bodyHandler;
    private QuestionProvider questionProvider;

    public TeaVerticle(int port) {
        this.port = port;
    }

    private Router questionRoutes(String root, Router router) {
        /*
        Get a list of all questions in the system.
         */
        router.get(root + "questions").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();

            logger.info("Received request GET for all questions");

            List<String> questions = questionProvider.getQuestions();
            JsonArray array = new JsonArray(questions);
            response
                    .putHeader("content-type", "application/json")
                    .end(array.toString());

        });
        /*
        Get the question info given the name
         */
        router.get(root + "question/:language/:questionName").handler(routingContext -> {
            HttpServerRequest request = routingContext.request();
            Language language = Language.valueOf(request.getParam("language").toUpperCase());
            String questionName = request.getParam("questionName");

            logger.info("Received request to GET question for " + language + " " + questionName);

            CodingQuestion question = questionProvider.getQuestionInfo(language, questionName);
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(question));
        });
        /*
        Post your solution text for a coding question
         */
        router.post(root + "answer/:language/:questionName").handler(routingContext -> {
            HttpServerRequest request = routingContext.request();
            Language language = Language.valueOf(request.getParam("language").toUpperCase());
            String questionName = request.getParam("questionName");

            logger.info("Received request to POST answer to " + language + " " + questionName);

            CodeAnswerResult result = questionProvider.testAnswer(routingContext.getBodyAsString(), language, questionName);
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "application/json")
                    .end(Json.encode(result));
        });
        return router;
    }

    @Override
    public void start(Future<Void> fut) {
        bodyHandler = BodyHandler.create();
        Router router = Router
                .router(vertx);
        router.route().handler(bodyHandler);

        questionProvider = new QuestionProvider();

        router.get("/ping").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/plain")
                    .end("pong");
        });
        StaticHandler staticHandler = StaticHandler
                .create("org/hombro/tea/webroot");
        router.route("/tea/app/*").handler(staticHandler);
        router = questionRoutes("/tea/", router);

        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        config().getInteger("http.port", port),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                            } else {
                                fut.fail(result.cause());
                            }
                        }
                );
    }
}
