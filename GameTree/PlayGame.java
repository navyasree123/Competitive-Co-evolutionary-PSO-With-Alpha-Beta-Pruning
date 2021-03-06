package GameTree;

import Game.Checkers.Checkers;
import NeuralNetwork.NeuralNetwork;

/**
 * Created by tinkie101 on 2015/07/01.
 */
public class PlayGame
{
	public static final int PLAYER1 = 1;
	public static final int PLAYER2 = 2;

	public int MAX_NUM_MOVES;

	private int PLY_DEPTH;
	private Boolean ALPHA_BETA;
	private double probability;


	private NeuralNetwork tempPlayer1NeuralNet;
	private NeuralNetwork tempPlayer2NeuralNet;

	public PlayGame(final NeuralNetwork tempPlayer1NeuralNet, final NeuralNetwork tempPlayer2NeuralNet, int ply_depth, Boolean alpha_beta, int max_moves, double probability){
		this.tempPlayer1NeuralNet =  tempPlayer1NeuralNet;
		this.tempPlayer2NeuralNet = tempPlayer2NeuralNet;

		this.MAX_NUM_MOVES = max_moves;
		this.PLY_DEPTH = ply_depth;
		this.ALPHA_BETA = alpha_beta;
		this.probability = probability;
	}

	public Integer play()
	{
		try
		{
			Checkers game = new Checkers();
			GameTree gameTree;
			Node nextMove;

			int moveCount = 0;
			while (moveCount < MAX_NUM_MOVES)
			{
				if (game.hasLost(PLAYER1))
				{
					//Update performance measure for Loss
					return 0;
				} else
				{
					gameTree = new GameTree(game, tempPlayer1NeuralNet);
					if(tempPlayer1NeuralNet == null){
						nextMove = gameTree.getRandomMove(PLAYER1);
					}
					else if(ALPHA_BETA == null){
						gameTree.generateRandomPruneTree(PLAYER1, PLY_DEPTH, probability);
						nextMove = gameTree.getBestMove(PLAYER1);
					}
					else{
						gameTree.generateTree(PLAYER1, PLY_DEPTH, ALPHA_BETA);
						nextMove = gameTree.getBestMove(PLAYER1);
					}
					game = nextMove.getGame();
				}

				if (game.hasLost(PLAYER2))
				{
					//Update performance measure for Win
					return 1;
				} else
				{
					gameTree = new GameTree(game, tempPlayer2NeuralNet);

					if(tempPlayer2NeuralNet == null){
						nextMove = gameTree.getRandomMove(PLAYER2);
					}
					else if(ALPHA_BETA == null){
						gameTree.generateRandomPruneTree(PLAYER2, PLY_DEPTH, probability);
						nextMove = gameTree.getBestMove(PLAYER2);
					}
					else {
						gameTree.generateTree(PLAYER2, PLY_DEPTH, ALPHA_BETA);
						nextMove = gameTree.getBestMove(PLAYER2);
					}
					game = nextMove.getGame();
				}
				moveCount++;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
		return 2;
	}
}
