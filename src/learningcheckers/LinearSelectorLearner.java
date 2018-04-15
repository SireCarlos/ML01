package learningcheckers;

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
        //TODO: Bitte Implementieren, also selector verbessern lassen
        return null;
    }
    
    
    /**
     * Lernt aus dem Spielverlauf, der durch li gegeben ist.
     * Verbessert den gegebenen Selector s, wobei s sich an den Bewertungen von teacher orientiert.
     */
    private void trainSupervised(LinearSelector s, List<Board> li, LinearSelector teacher) {
        //TODO: Bitte Implementieren!
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
	    	for (int i = 0; i < numberOfGames; i++) {
            Game g = new Game(learned, base, false);
            if (g.run(false) == 1) gamesWon++;
            g.closeGameWindow();
        }
        return gamesWon/numberOfGames;
    }
    
    

    
    public static void main(String[] args) {
        LinearSelectorLearner learner = new LinearSelectorLearner();
        LinearSelector base = new LinearSelector(8, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0);
        LinearSelector learned = 
                learner.learnUnsupervised(1000000, 0.8, base);
        System.out.println(learned);
        System.out.println(learner.fractionOfGamesWon(base, learned, 900));
    }
}

