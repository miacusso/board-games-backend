package boardgames.server;

import boardgames.db.PlayerDBO;

public class GameResultResponse {

	private PlayerDBO winner;

	public PlayerDBO getWinner() {
		return winner;
	}

	public void setWinner(PlayerDBO winner) {
		this.winner = winner;
	}

}
