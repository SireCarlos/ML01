package learningcheckers;

import java.util.ArrayList;
import java.util.List;

public class LinearSelectorLearner {
    
    
    public LinearSelectorLearner() {}
    
    
    
    /**
     * Lernt bessere Parameter für einen LinearSelector.
     * Dabei werden Testspiele erzeugt und sowohl Spielstände sowohl mit dem teacher als auch
     * mit dem zu lernenden Selektor bewertet. Der zu lernende Selektor lernt also
     * wie der teacher zu spielen.
     *   
     * @param rounds Anzahl der Testspiele die maximal durchgeführt werden.
     * @param goalFraction Der Lernalgorithmus bricht ab, wenn der gelernte 
     *                     Selektor mindestens diesen Bruchteil an Testspielen
     *                     gegen den gegebenen base-Selektor gewinnt. 
     * @param base Der Selektor mit dessen Parametern der Lernvorgang beginnt
     *             und der als Vergleich zum Ermitteln des Lernerfolgs herangezogen wird.
     * @param teacher Der Selektor von dem gelernt werden soll.
     * @return Den gelernten Selektor.
     */
    public LinearSelector learnSupervised(int rounds, double goalFraction, LinearSelector base, LinearSelector teacher) {
    	LinearSelector learner = (LinearSelector)base.clone();
    	for (int round = 0; round < rounds; rounds++) {
    		Game g = new Game(base, learner, false);
            g.run(false);
            g.closeGameWindow();
            List<Board> li = g.getHistory();
            trainSupervised(learner, li, teacher);
            double result = fractionOfGamesWon(base, learner, 100);
            if(result > goalFraction) {
            	System.out.println("result > goalFraction: " + result + " after " + round + " rounds");
            	return learner;
            }
    	}
    	System.out.println("Maximum rounds played");
        return learner;
    }
    
    
    /**
     * Lernt aus dem Spielverlauf, der durch li gegeben ist.
     * Verbessert den gegebenen Selector s, wobei s sich an den Bewertungen von teacher orientiert.
     */
    private void trainSupervised(LinearSelector s, List<Board> li, LinearSelector teacher) {    		
	   	double c = 0.01;
    	double factBasis = s.getFactBasis();
	    double factNrPiecesSelf = s.getFactNrPiecesSelf();
	    double factNrPiecesOther = s.getFactNrPiecesOther();
	    double factNrKingsSelf = s.getFactNrKingsSelf();
	    double factNrKingsOther = s.getFactNrKingsOther();
	    double factNrThreatenedPiecesSelf = s.getFactNrThreatenedPiecesSelf();
	    double factNrThreatenedPiecesOther = s.getFactNrThreatenedPiecesOther();
	    double factNrStuckSelfPieces = s.getFactNrStuckSelfPieces();
	    double factNrStuckOtherPieces = s.getFactNrStuckOtherPieces();
		for(Board b : li) {
		   	double fehler = teacher.evaluate(b, 1) - s.evaluate(b, 1);
		   	factBasis = factBasis + c * fehler * s.getFactBasis();
		   	factNrPiecesSelf = factNrPiecesSelf + c * fehler * s.getFactNrPiecesSelf();
		   	factNrPiecesOther = factNrPiecesOther + c * fehler * s.getFactNrPiecesOther();
		   	factNrKingsSelf = factNrKingsSelf + c * fehler * s.getFactNrKingsSelf();
		   	factNrKingsOther = factNrKingsOther + c * fehler * s.getFactNrKingsOther();
		   	factNrThreatenedPiecesSelf = factNrThreatenedPiecesSelf + c * fehler * s.getFactNrThreatenedPiecesSelf();
		   	factNrThreatenedPiecesOther = factNrThreatenedPiecesOther + c * fehler * s.getFactNrThreatenedPiecesOther();
		   	factNrStuckSelfPieces = factNrStuckSelfPieces + c * fehler * s.getFactNrStuckSelfPieces();
		   	factNrStuckOtherPieces = factNrStuckOtherPieces + c * fehler * s.getFactNrStuckOtherPieces();		   	
		}
		s.setFactBasis(factBasis);
		s.setFactNrPiecesSelf(factNrPiecesSelf);
		s.setFactNrPiecesOther(factNrPiecesOther);
		s.setFactNrKingsSelf(factNrKingsSelf);
		s.setFactNrKingsOther(factNrKingsOther);
		s.setFactNrThreatenedPiecesSelf(factNrThreatenedPiecesSelf);
		s.setFactNrThreatenedPiecesOther(factNrThreatenedPiecesOther);
		s.setFactNrStuckSelfPieces(factNrStuckSelfPieces);
		s.setFactNrStuckOtherPieces(factNrStuckOtherPieces);
    }
    
    
    
    /**
     * Lernt selbsständig bessere Parameter für einen LinearSelector.
     * Dabei werden Trainingsdaten automatisch über Testspiele erzeugt.
     *   
     * @param rounds Anzahl der Testspiele die maximal durchgeführt werden.
     * @param goalFraction Der Lernalgorithmus bricht ab, wenn der gelernte 
     *                     Selektor mindestens diesen Bruchteil an Testspielen
     *                     gegen den gegebenen base-Selektor gewinnt. 
     * @param base Der Selektor mit dessen Parametern der Lernvorgang beginnt
     *             und der als Vergleich zum Ermitteln des Lernerfolgs herangezogen wird.
     * @return Den gelernten Selektor.
     */
    public LinearSelector learnUnsupervised(int rounds, double goalFraction, LinearSelector base) {
        //TODO: Bitte Implementieren!
        return null;
    }
    
    
    /**
     * Lernt aus dem Spielverlauf, der durch li gegeben ist.
     * Verbessert den gegebenen Selector s. 
     */
    private void trainUnsupervised(LinearSelector s, List<Board> li) {
        //TODO: Bitte Implementieren!
    }
    
    
    /**
     * Führt numberOfGames Damespiele durch, wobei ein Spieler
     * seine Züge mittels base-Selektor ausführt und der andere
     * spieler mittels learned-Selector.
     * @return Den Bruchteil der Spiele, der von learned gewonnen wurde.
     */
    private double fractionOfGamesWon(LinearSelector base, LinearSelector learned, int numberOfGames){
	    	int gamesWon = 0;
	    	int noTie = 0;
	    	for (int i = 0; i < numberOfGames; i++) {
            Game g = new Game(base, learned, false);
            int winner = g.run(false);
            if (winner == 2) {
            		gamesWon++;
            		noTie++;
            }
            else if(winner == 1) {
            		noTie++;
            }
            g.closeGameWindow();
        }
        return gamesWon/noTie;
    }
    
    

    
    public static void main(String[] args) {
        LinearSelectorLearner learner = new LinearSelectorLearner();
        LinearSelector base = new LinearSelector(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0);
        LinearSelector learned = learner.learnUnsupervised(1000000, 0.8, base);
        HumanIntuitionLinearSelector human = new HumanIntuitionLinearSelector();
        //System.out.println(learned);
        System.out.println("base vs human " + learner.fractionOfGamesWon(base, human, 100));
        System.out.println("human vs base " + learner.fractionOfGamesWon(human, base, 100));
        learner.learnSupervised(100, 0.70, base, base);
    }
}


