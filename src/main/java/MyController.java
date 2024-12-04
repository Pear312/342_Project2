import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class MyController implements Initializable {

    @FXML
    private AnchorPane exitRoot, menuRoot, gameRoot, optionsRoot, eCRoot;

    @FXML
    private Label exitLabel, cTurn, pHand, ante, pairP, play, winnings, logBox;

    @FXML
    private ImageView h11, h12, h13, h21, h22, h23, d1, d2, d3;

    @FXML
    private Button b5, b10, b15, b20, b25, lAction, rAction, lAction1, lAction2, rAction1, rAction2, lAction3, rAction3, rAction4, replayQH, replay;

    static Player playerOne;
    static Player playerTwo;
    static Dealer theDealer;
    static Player currentPlayer;

    public MyController gameController;


    boolean anteDone, p1F, p2F;
    PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
    String[] handOne, handTwo, dealerHand;
    Image mainC1, mainC2, mainC3, secondaryC1, secondaryC2, secondaryC3, dC1, dC2, dC3;
    Image cardBack = new Image("images/Cards/cardBack.png");
    String log = "";


    @Override
    public void initialize(URL location, ResourceBundle resources){
        // Auto-generated method stub
    }


    public void setAnte(int value){
        ante.setText("$" + value);
    }


    public void setPairP(int value){
        pairP.setText("$" + value);
    }


    public void dealerCardFlip(){
        d1.setImage(cardBack);
        d2.setImage(cardBack);
        d3.setImage(cardBack);
    }


    public void enableButtons(){
        b5.setDisable(false);
        b10.setDisable(false);
        b15.setDisable(false);
        b20.setDisable(false);
        b25.setDisable(false);
    }



    public void setHandOne(){
        handOne = ThreeCardLogic.getCardImages(playerOne.getHand());
        mainC1 = new Image("images/Cards/" + handOne[0]);
        mainC2 = new Image("images/Cards/" + handOne[1]);
        mainC3 = new Image("images/Cards/" + handOne[2]);

        h11.setImage(mainC1);
        h12.setImage(mainC2);
        h13.setImage(mainC3);
    }



    public void setHandTwo(){
        handTwo = ThreeCardLogic.getCardImages(playerTwo.getHand());
        secondaryC1 = new Image("images/Cards/" + handTwo[0]);
        secondaryC2 = new Image("images/Cards/" + handTwo[1]);
        secondaryC3 = new Image("images/Cards/" + handTwo[2]);

        h21.setImage(secondaryC1);
        h22.setImage(secondaryC2);
        h23.setImage(secondaryC3);
    }



    public void phase2(){
        setTurn(1);

        theDealer = new Dealer();
        theDealer.setDealerHand(theDealer.dealHand());
        playerOne.setHand(theDealer.dealHand());
        playerTwo.setHand(theDealer.dealHand());

        dealerCardFlip();
        d1.setFitHeight(90);
        d1.setFitWidth(62);
        d1.setPreserveRatio(true);
        d2.setFitHeight(90);
        d2.setFitWidth(62);
        d2.setPreserveRatio(true);
        d3.setFitHeight(90);
        d3.setFitWidth(62);
        d3.setPreserveRatio(true);

        setHandOne();
        setHandTwo();

        h11.setFitHeight(90);
        h11.setFitWidth(62);
        h11.setPreserveRatio(true);
        h12.setFitHeight(90);
        h12.setFitWidth(62);
        h12.setPreserveRatio(true);
        h13.setFitHeight(90);
        h13.setFitWidth(62);
        h13.setPreserveRatio(true);

        h21.setFitHeight(50);
        h21.setFitWidth(35);
        h21.setPreserveRatio(true);
        h22.setFitHeight(50);
        h22.setFitWidth(35);
        h22.setPreserveRatio(true);
        h23.setFitHeight(50);
        h23.setFitWidth(35);
        h23.setPreserveRatio(true);

        lAction.setVisible(false);
        lAction1.setVisible(false);
        lAction2.setVisible(true);
        rAction.setVisible(false);
        rAction1.setVisible(false);
        rAction2.setVisible(true);

        b5.setDisable(true);
        b10.setDisable(true);
        b15.setDisable(true);
        b20.setDisable(true);
        b25.setDisable(true);
    }



    public void phase3(){
        setTurn(1);

        dealerHand = ThreeCardLogic.getCardImages(theDealer.getDealerHand());
        dC1 = new Image("images/Cards/" + dealerHand[0]);
        dC2 = new Image("images/Cards/" + dealerHand[1]);
        dC3 = new Image("images/Cards/" + dealerHand[2]);

        d1.setImage(dC1);
        d2.setImage(dC2);
        d3.setImage(dC3);

        if(!ThreeCardLogic.checkQueenHigh(theDealer.getDealerHand())){
            log = "";
            log += "Dealer does not have at least Queen high\nante wager is pushed\n";
            replayQH.setVisible(true);
        }
        else{
            if(!p1F){
                int winner = ThreeCardLogic.compareHands(theDealer.getDealerHand(), playerOne.getHand());
                calcResults(playerOne, "one", winner);
            }
            if(!p2F){
                int winner2 = ThreeCardLogic.compareHands(theDealer.getDealerHand(), playerTwo.getHand());
                calcResults(playerTwo,"two", winner2);
            }
            replay.setVisible(true);
        }
        logBox.setText(log);
        log = "";

    }



    public void calcResults(Player player, String num, int winner){
        if(winner == 0){
            log += "Tie\n";
        }
        else if(winner == 1){
            log += "Player " + num + " loses to Dealer\n";
            log += "Player " + num + " loses Pair Plus\n";
            player.setTotalWinnings(player.getTotalWinnings() - player.getAnteBet() - player.getPlayBet() - player.getPairPlusBet());
            winnings.setText("Winnings: $" + player.getTotalWinnings());
        }
        else if(winner == 2){
            log += "Player " + num + " beats Dealer\n";
            log += "Player " + num + " wins Pair Plus\n";

            int basicWager = (player.getAnteBet() + player.getPlayBet()) * 2;
            int PPwager = ThreeCardLogic.evalPPWinnings(player.getHand(), player.getPairPlusBet());
            player.setTotalWinnings(player.getTotalWinnings() + basicWager + PPwager);
            winnings.setText("Winnings: $" + player.getTotalWinnings());
        }

    }




    public void play(ActionEvent e){
        currentPlayer.setPlayBet(currentPlayer.getAnteBet());
        play.setText("$" + currentPlayer.getPlayBet());

        lAction2.setVisible(false);
        rAction2.setVisible(false);
        lAction3.setVisible(true);
        rAction3.setVisible(true);

        setTurn(2);
    }



    public void fold(ActionEvent e){
        lAction2.setVisible(false);
        rAction2.setVisible(false);
        lAction3.setVisible(true);
        rAction3.setVisible(true);
        currentPlayer.setTotalWinnings(currentPlayer.getTotalWinnings() - currentPlayer.getAnteBet() - currentPlayer.getPairPlusBet());
        p1F = true;
        setTurn(2);
    }



    public void play2(ActionEvent e){
        currentPlayer.setPlayBet(currentPlayer.getAnteBet());
        play.setText("$" + currentPlayer.getPlayBet());

        lAction3.setVisible(false);
        rAction3.setVisible(false);
        rAction4.setVisible(false);
        phase3();
    }



    public void fold2(ActionEvent e){
        lAction3.setVisible(false);
        rAction3.setVisible(false);

        currentPlayer.setTotalWinnings(currentPlayer.getTotalWinnings() - currentPlayer.getAnteBet() - currentPlayer.getPairPlusBet());
        p2F = true;
        phase3();
    }



    public void fold3(ActionEvent e){
        lAction3.setVisible(false);
        rAction4.setVisible(false);
        currentPlayer.setTotalWinnings(currentPlayer.getTotalWinnings() - currentPlayer.getAnteBet() - currentPlayer.getPairPlusBet());
        p1F = true;
        phase3();
    }



    public void betPressed(ActionEvent e){
        Button pressedButton = (Button) e.getSource();
        String buttonText = pressedButton.getText();

        if(anteDone){
            currentPlayer.setPairPlusBet(Integer.parseInt(buttonText.substring(1)));
            setPairP(currentPlayer.getPairPlusBet());

            lAction1.setDisable(false);
        }
        else{
            currentPlayer.setAnteBet(Integer.parseInt(buttonText.substring(1)));
            setAnte(currentPlayer.getAnteBet());

            lAction.setDisable(false);
            rAction.setDisable(false);
        }
    }



    public void deal1(ActionEvent e){
        anteDone = true;

        lAction.setVisible(false);
        lAction1.setVisible(true);
        rAction.setVisible(false);
        rAction1.setVisible(true);

        lAction1.setDisable(true);
    }



    public void deal2(ActionEvent e){
        if(playerTwo.getAnteBet() == 0){
            newAnte(2);
        }
        else{
            phase2();
        }
    }



    public void skip(ActionEvent e){
        currentPlayer.setPairPlusBet(0);
        if(playerTwo.getAnteBet() == 0){
            newAnte(2);
        }
        else{
            phase2();
        }
    }



    public void clear(ActionEvent e) throws IOException{
        lAction.setDisable(true);
        currentPlayer.setAnteBet(0);
        ante.setText("Ante");
    }



    public void setTurn(int playerNum){
        cTurn.setText("Current Turn: P" + playerNum);
        if (playerNum == 1){
            currentPlayer = playerOne;
            pHand.setText("P2 Hand");
            h11.setImage(mainC1);
            h12.setImage(mainC2);
            h13.setImage(mainC3);
            h21.setImage(secondaryC1);
            h22.setImage(secondaryC2);
            h23.setImage(secondaryC3);
        }
        else{
            currentPlayer = playerTwo;
            pHand.setText("P1 Hand");
            h21.setImage(mainC1);
            h22.setImage(mainC2);
            h23.setImage(mainC3);
            h11.setImage(secondaryC1);
            h12.setImage(secondaryC2);
            h13.setImage(secondaryC3);
        }
        winnings.setText("Winnings: $" + currentPlayer.getTotalWinnings());

        if(playerOne.getAnteBet() != 0 && playerTwo.getAnteBet() != 0){
            ante.setText("$" + currentPlayer.getAnteBet());
            pairP.setText("$" + currentPlayer.getPairPlusBet());
            play.setText("$" + currentPlayer.getPlayBet());
        }
    }



    public void setTurn2(int playerNum){
        cTurn.setText("Current Turn: P" + playerNum);
        if (playerNum == 1){
            currentPlayer = playerOne;
            pHand.setText("P2 Hand");
        }
        else{
            currentPlayer = playerTwo;
            pHand.setText("P1 Hand");
        }
        winnings.setText("Winnings: $" + currentPlayer.getTotalWinnings());
        ante.setText("Ante");
        pairP.setText("Pair\nPlus");
        play.setText("Play");

    }


    public void newRound(ActionEvent e){
        newAnte(1);
        setTurn2(1);
        playerOne.setPlayBet(0);
        playerOne.setPairPlusBet(0);
        playerOne.setAnteBet(0);
        playerTwo.setPlayBet(0);
        playerTwo.setPairPlusBet(0);
        playerTwo.setAnteBet(0);
        h11.setImage(null);
        h12.setImage(null);
        h13.setImage(null);
        h21.setImage(null);
        h22.setImage(null);
        h23.setImage(null);
        d1.setImage(null);
        d2.setImage(null);
        d3.setImage(null);
        logBox.setText("");
    }



    public void newAnte(int player){
        lAction.setDisable(true);
        rAction.setDisable(true);
        lAction.setVisible(true);
        rAction.setVisible(true);

        enableButtons();

        lAction1.setVisible(false);
        lAction2.setVisible(false);
        rAction1.setVisible(false);
        rAction2.setVisible(false);
        lAction3.setVisible(false);
        rAction3.setVisible(false);
        rAction4.setVisible(false);
        replay.setVisible(false);
        replayQH.setVisible(false);

        play.setText("Play");
        ante.setText("Ante");
        pairP.setText("Pair\nPlus");

        anteDone = false;
        setTurn(player);
    }


    public void replayQH(ActionEvent e){
        logBox.setText("");
        replayQH.setVisible(false);

        if(!p1F || !p2F){
            play.setText("Play");
        }

        theDealer.setDealerHand(theDealer.dealHand());
        playerOne.setHand(theDealer.dealHand());
        playerTwo.setHand(theDealer.dealHand());
        dealerCardFlip();

        if(!p1F){
            playerOne.setPlayBet(0);
            setHandOne();
            setTurn(1);
            lAction3.setVisible(true);
            rAction4.setVisible(true);

        }
        else if(!p2F){
            playerTwo.setPlayBet(0);
            setHandTwo();
            setTurn(2);
            lAction3.setVisible(true);
            rAction3.setVisible(true);
        }
        else if(!p1F && !p2F){
            setHandOne();
            setHandTwo();
            setTurn(1);
            lAction1.setVisible(true);
            rAction1.setVisible(true);
        }
    }



    public void freshStart(){
        playerOne = new Player();
        playerTwo = new Player();
        theDealer = new Dealer();

        lAction.setDisable(true);
        rAction.setDisable(true);

        enableButtons();

        lAction1.setVisible(false);
        lAction2.setVisible(false);
        rAction1.setVisible(false);
        rAction2.setVisible(false);
        lAction3.setVisible(false);
        rAction3.setVisible(false);
        rAction4.setVisible(false);
        replayQH.setVisible(false);
        replay.setVisible(false);

        logBox.setText("");

        anteDone = false;
        p1F = false;
        p2F = false;
        setTurn(1);
    }



    public void exitAnimation() {
        PauseTransition exitPause1 = new PauseTransition(Duration.seconds(0.5));
        PauseTransition exitPause2 = new PauseTransition(Duration.seconds(0.5));
        PauseTransition exitPause3 = new PauseTransition(Duration.seconds(0.5));
        PauseTransition exitPause4 = new PauseTransition(Duration.seconds(0.5));

        exitLabel.setText("Exiting");
        exitPause1.play();

        // Chain the pause transitions
        exitPause1.setOnFinished(e -> {
            exitLabel.setText("Exiting.");
            exitPause2.play();
        });
        exitPause2.setOnFinished(e -> {
            exitLabel.setText("Exiting..");
            exitPause3.play();
        });
        exitPause3.setOnFinished(e -> {
            exitLabel.setText("Exiting...");
            exitPause4.play();
        });
        exitPause4.setOnFinished(e -> {
            Platform.exit();
        });
    }


    public static Parent gRoot;
    public static Parent oRoot;
    public static Parent eRoot;
    public static Parent tRoot;

    public void playGame(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/game.fxml"));
        gRoot = loader.load();
        gameController = loader.getController();

        pause.setOnFinished(a -> menuRoot.getScene().setRoot(gRoot));
        pause.play();

        gameController.freshStart();
    }


    public void exitGame(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/exitScreen.fxml"));
        eRoot = loader.load();
        MyController myctr = loader.getController();

        pause.setOnFinished(a -> menuRoot.getScene().setRoot(eRoot));
        pause.play();
        myctr.exitAnimation();
    }


    public void optionsMenu(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/options.fxml"));
        oRoot = loader.load();

        gameRoot.getScene().setRoot(oRoot);
    }


    public void switchBackToGame(ActionEvent e){
        optionsRoot.getScene().setRoot(gRoot);
    }

    public void switchToExit(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/exitConfirmation.fxml"));
        Parent eRoot = loader.load();

        optionsRoot.getScene().setRoot(eRoot);
    }

    public void goBack(ActionEvent e){
        eCRoot.getScene().setRoot(oRoot);
    }

    public void goBack2(ActionEvent e){
        tRoot.getScene().setRoot(oRoot);
    }

    public void goToExit(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/exitScreen.fxml"));
        Parent root = loader.load();
        MyController myctr = loader.getController();

        pause.setOnFinished(a -> eCRoot.getScene().setRoot(root));
        pause.play();
        myctr.exitAnimation();
    }

    public void switchFreshStart(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/game.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();

        pause.setOnFinished(a -> optionsRoot.getScene().setRoot(root));
        pause.play();

        gameController.freshStart();
    }

    public void switchToThemes(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/newLook.fxml"));
        tRoot = loader.load();
        MyController myctr = loader.getController();

        pause.setOnFinished(a -> optionsRoot.getScene().setRoot(tRoot));
        pause.play();

    }

    private void applyTheme(String cssPath) {
        gRoot.getStylesheets().clear();
        gRoot.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

    }

    public void setChristmas(ActionEvent e) {
        // Apply the Christmas theme to the game scene
        applyTheme("/styles/christmas.css");
    }

    public void setHallow(ActionEvent e) {
        // Apply the Halloween theme to the game scene
        applyTheme("/styles/halloween.css");
    }

    public void setDefault(ActionEvent e) {
        // Apply the default theme to the game scene
        applyTheme("/styles/game.css");
    }



}
