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
        LinearSelector selector = (LinearSelector)base.clone();
        Game g = new Game(selector, base, false);
        //fractionOfGame
        //trainSupervised(selector, )
        return null;
    }
    
    
    /**
     * Lernt aus dem Spielverlauf, der durch li gegeben ist.
     * Verbessert den gegebenen Selector s, wobei s sich an den Bewertungen von teacher orientiert.
     */
    private void trainSupervised(LinearSelector s, List<Board> li, LinearSelector teacher) {    		
    	 	for(Board b : li) {
		   	double fehler = teacher.evaluate(b, 1) - s.evaluate(b, 1);
		   	double newFactBasis = 0 + 0.01 * fehler * s.getFactBasis();
		    double newFactNrPiecesSelf = 0 + 0.01 * fehler * s.getFactNrPiecesSelf();
		    double newFactNrPiecesOther = 0 + 0.01 * fehler * s.getFactNrKingsOther();
		    double newFactNrKingsSelf = 0 + 0.01 * fehler * s.getFactNrKingsSelf();
		    double newFactNrKingsOther = 0 + 0.01 * fehler * s.getFactNrKingsOther();
		    double newFactNrThreatenedPiecesSelf = 0 + 0.01 * fehler * s.getFactNrThreatenedPiecesSelf();
		    double newFactNrThreatenedPiecesOther = 0 + 0.01 * fehler * s.getFactNrThreatenedPiecesOther();
		    double newFactNrStuckSelfPieces = 0 + 0.01 * fehler * s.getFactNrStuckSelfPieces();
		    double newFactNrStuckOtherPieces = 0 + 0.01 * fehler * s.getFactNrStuckOtherPieces();
	   	}
    	 	
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
            Game g = new Game(learned, base, false);
            int winner = g.run(false);
            if (winner == 1) {
            		gamesWon++;
            		noTie++;
            }
            else if(winner == 2) {
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
        System.out.println(learner.fractionOfGamesWon(base, human, 1000));
        System.out.println(learner.fractionOfGamesWon(human, base, 1000));
    }
}


