import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.io.IOException;

public class RiskView extends JFrame {
    private JLabel statusLabel = new JLabel();
    private JLabel continentsLabel = new JLabel();

    private JPanel mainPanel = new JPanel();
    private JPanel graphMapPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JPanel combinedMapPrintInfoPanel = new JPanel();
    private JPanel continentInfoPanel = new JPanel();

    private JTextArea printInfoArea = new JTextArea();

    private JList startingTerritory = new JList();
    private JList destinationTerritory = new JList();

    private JComboBox troopsBox = new JComboBox();

    private JButton functionButton = new JButton();
    private JButton skipButton = new JButton();

    private JScrollPane startingTerritoryScrollPane;
    private JScrollPane destinationTerritoryScrollPane;
    private JScrollPane printInfoScrollPane;

    private RiskModel model;

    public RiskView() throws IOException {
        super("Risk Game");
        this.add(mainPanel);

        model = new RiskModel();

//        statusLabel.setText("It's my turn.");

        getNumberPlayerDialog();

        printInfoScrollPane = new JScrollPane(printInfoArea);
        printInfoArea.setEditable(false);
//        printInfoArea.append("Welcome to Risk Game!");

        startingTerritory.setModel(model.allCountriesJList);
        startingTerritory.setLayoutOrientation(JList.VERTICAL);
        startingTerritory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        startingTerritoryScrollPane = new JScrollPane(startingTerritory);

        destinationTerritory.setModel(model.allContinentsJList);
        destinationTerritory.setLayoutOrientation(JList.VERTICAL);
        destinationTerritory.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        destinationTerritoryScrollPane = new JScrollPane(destinationTerritory);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("src/RiskMap.jpg"));
        graphMapPanel.add(label);

        continentInfoPanel.add(continentsLabel);

        combinedMapPrintInfoPanel.setLayout(new BorderLayout());
        combinedMapPrintInfoPanel.add(printInfoScrollPane,BorderLayout.WEST);
        combinedMapPrintInfoPanel.add(graphMapPanel,BorderLayout.EAST);

        operationPanel.setLayout(new BoxLayout(operationPanel,BoxLayout.Y_AXIS));
        operationPanel.add(startingTerritoryScrollPane);
        operationPanel.add(functionButton);
        operationPanel.add(destinationTerritoryScrollPane);
        operationPanel.add(troopsBox);
        operationPanel.add(skipButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(statusLabel,BorderLayout.NORTH);
        mainPanel.add(combinedMapPrintInfoPanel,BorderLayout.WEST);
        mainPanel.add(operationPanel,BorderLayout.CENTER);
        mainPanel.add(continentInfoPanel,BorderLayout.EAST);

        this.setLocation(200,50);
        this.setSize(720,360);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }



    public static int getNumberPlayerDialog(){
        int numberPlayer = 0;
        while(numberPlayer==0) {
            String input = JOptionPane.showInputDialog( "Please enter the number of players.(2-6)");
            if(!input.matches("[2-6]"))JOptionPane.showMessageDialog(null, "Invalid Input.", "Warning", JOptionPane.ERROR_MESSAGE);
            else {
                numberPlayer = Integer.parseInt(input);
            }
        }
        return numberPlayer;
    }

    public static void main(String[] args) throws IOException {
        new RiskView();
    }
}
