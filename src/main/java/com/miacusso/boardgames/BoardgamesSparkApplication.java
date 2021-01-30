package com.miacusso.boardgames;

import static spark.Spark.*;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.miacusso.boardgames.db.DatabaseConnector;
import com.miacusso.boardgames.db.GameDBO;
import com.miacusso.boardgames.db.PlayerDBO;

public class BoardgamesSparkApplication {

	public static void main(String[] args) {

		Gson gson = new Gson();
		DatabaseConnector dbConnector = new DatabaseConnector();

		get("/:game/players", "application/json", (req, res) -> {

			GameDBO gameDBO = new GameDBO();
			gameDBO.setId(Integer.valueOf(req.params("game")));

			List<PlayerDBO> dbResponse = dbConnector.retrievePlayersForGame(gameDBO);

			return dbResponse;

		}, gson::toJson);

		after("/:game/players", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET");
            res.header("Content-Type", "application/json");
			res.body(res.body().toLowerCase());
		});

		get("/:game/result-table", "application/json", (req, res) -> {

			GameDBO gameDBO = new GameDBO();
			gameDBO.setId(Integer.valueOf(req.params("game")));

			Map<String, Integer> dbResponse = dbConnector.retrieveResultsCountForGame(gameDBO);

			return dbResponse;

		}, gson::toJson);

		after("/:game/result-table", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET");
            res.header("Content-Type", "application/json");
			res.body(res.body().toLowerCase());
		});

		options("/:game/winner", (req, res) -> {

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

			res.header("Access-Control-Allow-Origin", "*");

			return "OK";

		});

		get("/:game/delete-result-table", (req, res) -> {

			GameDBO gameDBO = new GameDBO();
			gameDBO.setId(Integer.valueOf(req.params("game")));

			dbConnector.removeResultsForGame(gameDBO);

			return "OK";

		});

	}

}
