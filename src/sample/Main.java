package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {
    private String[] wordsToGuess = new String[]{"Washington","defenestration","piggy","timmeh!!!","balloon",
            "mathematics","noodle","aeiou","zebra","xylophone","abcde"};
    private SimpleIntegerProperty numberOfStrikes = new SimpleIntegerProperty(this, "numberOfStrikes", -1){
        public String toString() {return String.valueOf(Math.max(get(), 0));}
    };
    private Label guessedWordLabel = new Label();
    private Label numberOfGuessesLabel = new Label();
    private Label guessedLetters = new Label();
    private TextField guessTextBox = new TextField();
    private ImageView stickmanImage = new ImageView();
    {
        guessedWordLabel.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        numberOfGuessesLabel.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        guessTextBox.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        guessedLetters.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        guessedLetters.setTextFill(Paint.valueOf("red"));
    }
    public String wordToGuess() {
        double random = Math.random();
        return wordsToGuess[(int) Math.floorDiv(((long) (random * wordsToGuess.length)),(long)1)];
    }

    @Override
    public void start(Stage primaryStage) {
        new TreeviewTestProgram(primaryStage).main();
    }

    class TreeviewTestProgram {
        public Stage primaryStage;
        public Scene primaryScene;
        public VBox primaryVBox;
        public TreeviewTestProgram(Stage primaryStage) {
            this.primaryStage = primaryStage;
            this.primaryVBox = new VBox();
            primaryScene = new Scene(primaryVBox, 800, 600);
            primaryStage.setScene(primaryScene);
            primaryStage.show();
        }
        public void main() {
            Button hello = new Button("Hello");
            primaryVBox.getChildren().add(hello);
        }
    }

    class ProgramTemplate {
        public Stage primaryStage;
        public Scene primaryScene;
        public VBox primaryVBox;
        public ProgramTemplate(Stage primaryStage) {
            this.primaryStage = primaryStage;
            this.primaryVBox = new VBox();
            primaryScene = new Scene(primaryVBox, 800, 600);
            primaryStage.setScene(primaryScene);
            primaryStage.show();
        }
        public void main() {}
    }

    class BlackJackProgram {
        public Stage primaryStage;
        public BlackJackProgram(Stage primaryStage) {
            this.primaryStage = primaryStage;
        }
        public AtomicInteger randomNumber = new AtomicInteger(0);
        ObservableList<Integer> player = FXCollections.observableArrayList();
        Integer playerSum = new Integer(0);
        ObservableList<Integer> dealer = FXCollections.observableArrayList();
        Integer dealerSum = new Integer(0);
        Label label = new Label();
        Button hitButton = new Button("Hit");
        Button stayButton = new Button("Stay");
        Button clearButton = new Button("Clear");
        public int doRandomAndGet() {
            randomNumber.set((int) Math.floorMod((long) (Math.random() * 13), (long) 13.0) + 1);
            randomNumber.getAndUpdate(operand -> Math.min(operand,10));
            return randomNumber.get();
        }
        public void main() {
            label.setFont(Font.font("Arial",FontWeight.BOLD,20.0));
            hitButton.setFont(Font.font("Arial",FontWeight.BOLD,20.0));
            stayButton.setFont(Font.font("Arial",FontWeight.BOLD,20.0));
            clearButton.setFont(Font.font("Arial",FontWeight.BOLD,20.0));
            hitButton.setOnAction(actionEvent -> {
                if (playerSum<21) player.add(doRandomAndGet());
                if (dealerSum<17) dealer.add(doRandomAndGet());
            });
            stayButton.setOnAction(actionEvent -> {
                if (dealerSum<17) dealer.add(doRandomAndGet());
            });
            clearButton.setOnAction(actionEvent -> {
                player.clear();
                dealer.clear();
            });
            HBox hBoxPlayer = new HBox(); HBox hBoxPlayerCount = new HBox(new Label("Player"),hBoxPlayer, new Label());
            ((Label) hBoxPlayerCount.getChildren().get(2)).setFont(Font.font("Arial",20.0));
            ((Label) hBoxPlayerCount.getChildren().get(0)).setFont(Font.font("Arial",20.0));
            HBox hBoxDealer = new HBox(); HBox hBoxDealerCount = new HBox(new Label("Dealer"),hBoxDealer, new Label());
            ((Label) hBoxDealerCount.getChildren().get(2)).setFont(Font.font("Arial",20.0));
            ((Label) hBoxDealerCount.getChildren().get(0)).setFont(Font.font("Arial",20.0));
            VBox vBox = new VBox(hBoxPlayerCount,hBoxDealerCount, new HBox(hitButton,stayButton,clearButton));
            player.addListener(new ListChangeListener<Integer>() {
                @Override
                public void onChanged(Change<? extends Integer> change) {
                    hBoxPlayer.getChildren().clear();
                    hBoxPlayer.getChildren().addAll(change.getList().stream().map(integer -> {
                        Button button = new Button(integer + "");
                        button.setFont(Font.font("Arial", FontPosture.ITALIC,20.0));
                        return button;
                    }).toList());
                    playerSum = change.getList().stream().mapToInt(value -> value).sum();
                    ((Label) hBoxPlayerCount.getChildren().get(2)).setText(
                            playerSum + (playerSum > 21 ? " BUST!!!!!! LOL" : "")
                    );
                }
            });
            dealer.addListener(new ListChangeListener<Integer>() {
                @Override
                public void onChanged(Change<? extends Integer> change) {
                    hBoxDealer.getChildren().clear();
                    hBoxDealer.getChildren().addAll(change.getList().stream().map(integer -> {
                        Button button = new Button(integer + "");
                        button.setFont(Font.font("Arial", FontPosture.ITALIC,20.0));
                        return button;
                    }).toList());
                    dealerSum = change.getList().stream().mapToInt(value -> value).sum();
                    ((Label) hBoxDealerCount.getChildren().get(2)).setText(
                            dealerSum + (dealerSum > 21 ? " UHH... UM... BEGINNER'S LUCK!! :(" : "")
                    );
                }
            });
            Scene scene = new Scene(vBox,1200,600);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    }

    public void hangmanProgram(Stage primaryStage) {
        String wordToGuess = wordToGuess();
        guessedWordLabel.setText(hideWord(wordToGuess));
        numberOfGuessesLabel.setText("Number of strikes: " + numberOfStrikes.toString());
        //stickmanImage.setImage(new Image("/images/stickman0.png"));
        Button guessButton = new Button("Guess");
        EventHandler guess = e -> {
            String guessedLetter = guessTextBox.getText(); // replace this with input from user
            if (wordToGuess.toLowerCase(Locale.ROOT).contains(guessedLetter.toLowerCase(Locale.ROOT))) {
                guessedWordLabel.setText(showLetter(guessedLetter, wordToGuess, guessedWordLabel.getText()));
            } else {
                numberOfStrikes.set(numberOfStrikes.get() + 1);
                guessedLetters.setText(guessedLetters.getText()+" "+guessedLetter);
            }
        };
        guessButton.setOnAction(guess);
        guessTextBox.setOnAction(guess);

        HBox guessBox = new HBox(10, new Label("Guess a letter: "), guessTextBox, guessButton);
        guessBox.setPadding(new Insets(10));

        Group stickMan = drawStickMan(150,100,30);
        Button resetButton = new Button("Reset");
        VBox root = new VBox(10, stickMan,guessedWordLabel, numberOfGuessesLabel, guessedLetters,guessBox, resetButton);
        numberOfStrikes.addListener(observable -> {
            for (int i=0; i<stickMan.getChildren().size(); i++) {
                stickMan.getChildren().get(i).setVisible(i<numberOfStrikes.get());
                primaryStage.setHeight(
                        (Math.max(stickMan.prefHeight(0.0), 110.0))+
                                guessedWordLabel.getHeight()+
                                numberOfGuessesLabel.getHeight()+
                                guessBox.getHeight()+
                                resetButton.getHeight()+
                                root.getPadding().getTop()+
                                root.getPadding().getBottom()+
                                80.0
                );
            }
            numberOfGuessesLabel.setText("Number of strikes: " + numberOfStrikes.get());
        });
        numberOfStrikes.set(0);
        resetButton.setOnAction(actionEvent -> numberOfStrikes.set(0));
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root);
        root.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hangman");
        primaryStage.show();
    }

    private Group drawStickMan(double x0, double y0, double l0) {
        Group root = new Group();

        // Head
        Circle head = new Circle(x0, y0, l0);
        root.getChildren().add(head);

        // Body
        Line body = new Line(x0, y0+l0, x0, y0+3*l0);
        root.getChildren().add(body);

        // Arms
        Line arm1 = new Line(x0, y0+3/2*l0, x0-l0, y0+2*l0);
        Line arm2 = new Line(x0, y0+3/2*l0, x0+l0, y0+2*l0);
        root.getChildren().add(arm1);
        root.getChildren().add(arm2);

        // Legs
        Line leg1 = new Line(x0, y0+3*l0, x0-l0, y0+4*l0);
        Line leg2 = new Line(x0, y0+3*l0, x0+l0, y0+4*l0);
        root.getChildren().add(leg1);
        root.getChildren().add(leg2);

        return root;
    }

    private String hideWord(String word) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            hiddenWord.append("_");
        }
        return hiddenWord.toString();
    }

    private String showLetter(String letter, String word, String currentGuessedWord) {
        StringBuilder newGuessedWord = new StringBuilder(currentGuessedWord);
        for (int i = 0; i < word.length(); i++) {
            if (word.toLowerCase(Locale.ROOT).charAt(i) == letter.toLowerCase(Locale.ROOT).charAt(0)) {
                newGuessedWord.setCharAt(i, word.charAt(i));
            }
        }
        return newGuessedWord.toString();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class MatchingGame {
    private String[] wordsToGuess = new String[]{"Washington","defenestration","piggy","timmeh!!!","balloon",
            "mathematics","noodle"};
    private SimpleIntegerProperty numberOfStrikes = new SimpleIntegerProperty(this, "numberOfStrikes", -1){
        public String toString() {return String.valueOf(Math.max(get(), 0));}
    };
    private Label guessedWordLabel = new Label();
    private Label numberOfGuessesLabel = new Label();
    private Label guessedLetters = new Label();
    private TextField guessTextBox = new TextField();
    private ImageView stickmanImage = new ImageView();
    {
        guessedWordLabel.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        numberOfGuessesLabel.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        guessTextBox.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        guessedLetters.setFont(Font.font("Arial", FontWeight.BOLD,20.0));
        guessedLetters.setTextFill(Paint.valueOf("red"));
    }
    public String wordToGuess() {
        double random = Math.random();
        return wordsToGuess[(int) Math.floorDiv(((long) (random * wordsToGuess.length)),(long)1)];
    }

    public void start(Stage primaryStage) {
        hangmanProgram(primaryStage);
    }

    public void hangmanProgram(Stage primaryStage) {
        String wordToGuess = wordToGuess();
        guessedWordLabel.setText(hideWord(wordToGuess));
        numberOfGuessesLabel.setText("Number of strikes: " + numberOfStrikes.toString());
        //stickmanImage.setImage(new Image("/images/stickman0.png"));
        Button guessButton = new Button("Guess");
        EventHandler guess = e -> {
            String guessedLetter = guessTextBox.getText(); // replace this with input from user
            if (wordToGuess.toLowerCase(Locale.ROOT).contains(guessedLetter.toLowerCase(Locale.ROOT))) {
                guessedWordLabel.setText(showLetter(guessedLetter, wordToGuess, guessedWordLabel.getText()));
            } else {
                numberOfStrikes.set(numberOfStrikes.get() + 1);
                guessedLetters.setText(guessedLetters.getText()+" "+guessedLetter);
            }
        };
        guessButton.setOnAction(guess);
        guessTextBox.setOnAction(guess);

        HBox guessBox = new HBox(10, new Label("Guess a letter: "), guessTextBox, guessButton);
        guessBox.setPadding(new Insets(10));

        Group stickMan = drawStickMan(150,100,30);
        Button resetButton = new Button("Reset");
        VBox root = new VBox(10, stickMan,guessedWordLabel, numberOfGuessesLabel, guessedLetters,guessBox, resetButton);
        numberOfStrikes.addListener(observable -> {
            for (int i=0; i<stickMan.getChildren().size(); i++) {
                stickMan.getChildren().get(i).setVisible(i<numberOfStrikes.get());
                primaryStage.setHeight(
                        (Math.max(stickMan.prefHeight(0.0), 110.0))+
                                guessedWordLabel.getHeight()+
                                numberOfGuessesLabel.getHeight()+
                                guessBox.getHeight()+
                                resetButton.getHeight()+
                                root.getPadding().getTop()+
                                root.getPadding().getBottom()+
                                80.0
                );
            }
            numberOfGuessesLabel.setText("Number of strikes: " + numberOfStrikes.get());
        });
        numberOfStrikes.set(0);
        resetButton.setOnAction(actionEvent -> numberOfStrikes.set(0));
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root);
        root.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hangman");
        primaryStage.show();
    }
    private String hideWord(String word) {
        StringBuilder hiddenWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            hiddenWord.append("_");
        }
        return hiddenWord.toString();
    }
    private Group drawStickMan(double x0, double y0, double l0) {
        Group root = new Group();

        // Head
        Circle head = new Circle(x0, y0, l0);
        root.getChildren().add(head);

        // Body
        Line body = new Line(x0, y0+l0, x0, y0+3*l0);
        root.getChildren().add(body);

        // Arms
        Line arm1 = new Line(x0, y0+3/2*l0, x0-l0, y0+2*l0);
        Line arm2 = new Line(x0, y0+3/2*l0, x0+l0, y0+2*l0);
        root.getChildren().add(arm1);
        root.getChildren().add(arm2);

        // Legs
        Line leg1 = new Line(x0, y0+3*l0, x0-l0, y0+4*l0);
        Line leg2 = new Line(x0, y0+3*l0, x0+l0, y0+4*l0);
        root.getChildren().add(leg1);
        root.getChildren().add(leg2);

        return root;
    }
    private String showLetter(String letter, String word, String currentGuessedWord) {
        StringBuilder newGuessedWord = new StringBuilder(currentGuessedWord);
        for (int i = 0; i < word.length(); i++) {
            if (word.toLowerCase(Locale.ROOT).charAt(i) == letter.toLowerCase(Locale.ROOT).charAt(0)) {
                newGuessedWord.setCharAt(i, word.charAt(i));
            }
        }
        return newGuessedWord.toString();
    }

    public static void main(String[] args) {}
}
