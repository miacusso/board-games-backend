package boardgames.server;

import static spark.Spark.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import boardgames.db.DatabaseConnector;
import boardgames.db.GameDBO;
import boardgames.db.PlayerDBO;

public class Server {

	public static void main(String[] args) {

		Gson gson = new Gson();
		DatabaseConnector dbConnector = new DatabaseConnector();

		get("/:game/players", "application/json", (req, res) -> {

			GameDBO gameDBO = new GameDBO();
			gameDBO.setId(Integer.valueOf(req.params("game")));

			List<PlayerDBO> dbResponse = dbConnector.retrievePlayersForGame(gameDBO);

			return dbResponse;

		}, gson::toJson);

		get("/:game/result-table", "application/json", (req, res) -> {

			GameDBO gameDBO = new GameDBO();
			gameDBO.setId(Integer.valueOf(req.params("game")));

			Map<PlayerDBO, Integer> dbResponse = dbConnector.retrieveResultsCountForGame(gameDBO);

			return dbResponse.entrySet().stream().collect(Collectors.toMap(e -> e.getKey().getName(), e -> e.getValue()));

		}, gson::toJson);

		options("/:game/winner", (req, res) -> {

			System.out.println("llegó el options");

			String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			res.header("Access-Control-Allow-Origin", "*");

			return "OK";

		});

		post("/:game/winner", "application/json", (req, res) -> {

			Integer game = Integer.valueOf(req.params("game"));
			GameResultResponse gameResult = gson.fromJson(req.body(), GameResultResponse.class);

			dbConnector.insertGameResult(gameResult.getWinner().getId(), game);

			return "OK";

		}, gson::toJson);

		after("/:game/players", (req, res) -> {
			System.out.println("le pegó al after 1");
			res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET");
			res.body(res.body().toLowerCase());
		});

		after("/:game/result-table", (req, res) -> {
			System.out.println("le pegó al after 2");
			res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET");
			res.body(res.body().toLowerCase());
		});

	}

}
