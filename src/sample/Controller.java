package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.BarChart;
import jdk.internal.org.objectweb.asm.commons.StaticInitMerger;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private GridPane gPane;
    @FXML
    private Slider sliderHumid;
    @FXML
    private Slider sliderTemp;
    @FXML
    private CheckBox check1;
    @FXML
    private CheckBox check2;
    @FXML
    private ListView lst1;
    @FXML
    private BarChart barGraph;
    @FXML
    private Button btnFlu;
    @FXML
    private Button btnHiv;
    @FXML
    private Button btnCoro;
    @FXML
    private Button btnCord;
    @FXML
    private Button btnstopFlu;
    @FXML
    private Button btnstopHiv;
    @FXML
    private Button btnstopCoronavirus;
    @FXML
    private Button btnstopCordyceps;
    Button[][] btn = new Button[20][20];
    int[][] gridData = new int[20][20];
    List<Flu> flu = new ArrayList<>();
    List<Hiv> hiv = new ArrayList<>();
    List<Cordyceps> cordyceps = new ArrayList<>();
    List<Coronavirus> coronavirus = new ArrayList<>();
    ArrayList<String> colors = new ArrayList<String>() {{
        add("4d4dff");
        add("6666ff");
        add("8080ff");
        add("ccccff");
        add("e6e6ff");
        add("ffffff");
        add("ffe6e6");
        add("ffb3b3");
        add("ff9999");
        add("ff6666");
        add("ff3333");
    }};
    boolean fluExist = false;
    boolean hivExist = false;
    boolean coronaExist = false;
    boolean stopFlu = false;
    boolean stopHiv = false;
    boolean stopCoronavirus = false;
    boolean stopCordyceps = false;
    int aliveFlu = 0;
    int aliveHiv = 0;
    int aliveCordyceps = 0;
    int aliveCoronavirus = 0;
    int deathFlu = 0;
    int deathHiv = 0;
    int deathCordyceps = 0;
    int deathCoronavirus = 0;
    private long graphTime = System.nanoTime();
    boolean displayCurrent = true;
    XYChart.Series<String, Number> series1;
    XYChart.Series<String, Number> series2;
    XYChart.Series<String, Number> series3;
    XYChart.Series<String, Number> series4;

    @FXML
    private void handleStart() {
        sliderTemp.setValue(50.0);//sets starting humidity and temperature at the middle
        sliderHumid.setValue(50.0);
        check1.setSelected(true);
        check2.setSelected(false);
        for(int i=0; i<20; i++){
            for(int j=0; j<20;j++){
                btn[i][j] = new Button();
                btn[i][j].setStyle("-fx-background-color:#" + colors.get(5) + "; -fx-background-radius: 0;");
                btn[i][j].setPrefWidth(150);
                gPane.add(btn[i][j], j, i);
                gridData[i][j]= 0;
            }
        }
        gPane.setGridLinesVisible(false);
        gPane.setVisible(true);
        graphData(true);
        btnFlu.setDisable(false);
        btnHiv.setDisable(false);
        btnCoro.setDisable(false);
        btnCord.setDisable(false);
        btnstopFlu.setDisable(false);
        btnstopHiv.setDisable(false);
        btnstopCordyceps.setDisable(false);
        btnstopCoronavirus.setDisable(false);
        start();
    }
    @FXML
    private void handlecheck1() {
        displayCurrent = true;//user wants to see the current disease counts
        check2.setSelected(false);
    }
    @FXML
    private void handlecheck2() {
        displayCurrent = false;//user wants to see all disease counts
        check1.setSelected(false);
    }
    @FXML
    private void graphData(boolean first) {
        if (first) {//creates new series
            series1 = new XYChart.Series<>();
            series2 = new XYChart.Series<>();
            series3 = new XYChart.Series<>();
            series4 = new XYChart.Series<>();
            barGraph.getData().addAll(series1, series2, series3, series4);
        }
        series1.getData().clear();//ensures graphs are not drawn on top of each other
        series2.getData().clear();
        series3.getData().clear();
        series4.getData().clear();
        if (displayCurrent) {//will display the current counts of all diseases
            series1.getData().add(new XYChart.Data<>("Flu", aliveFlu));
            series2.getData().add(new XYChart.Data<>("HIV", aliveHiv));
            series3.getData().add(new XYChart.Data<>("Coronavirus", aliveCoronavirus));
            series4.getData().add(new XYChart.Data<>("Cordyceps", aliveCordyceps));
        }
        else {//will display all counts of all diseases
            series1.getData().add(new XYChart.Data<>("Flu", aliveFlu + deathFlu));
            series2.getData().add(new XYChart.Data<>("HIV", aliveHiv + deathHiv));
            series3.getData().add(new XYChart.Data<>("Coronavirus", aliveCoronavirus + deathCoronavirus));
            series4.getData().add(new XYChart.Data<>("Cordyceps", aliveCordyceps + deathCordyceps));
        }
    }
    @FXML
    private void updateList() {//updates the listview of both current disease counts and all disease counts
        lst1.getItems().clear();
        lst1.getItems().add("Flu                                      " + aliveFlu + "                           " + (aliveFlu + deathFlu));
        lst1.getItems().add("HIV                                     " + aliveHiv + "                           " + (aliveHiv + deathHiv));
        lst1.getItems().add("Coronavirus                       " + aliveCoronavirus + "                           " + (aliveCoronavirus + deathCoronavirus));
        lst1.getItems().add("Cordyceps                         " + aliveCordyceps + "                           " + (aliveCordyceps + deathCordyceps));
    }
    @FXML
    private void handlebtnFlu() {
        fluExist = true;
        int randX = (int)(Math.random() * 20);//will continue to find a random valid spot to place a flu until one is found
        int randY = (int)(Math.random() * 20);
        while (gridData[randX][randY] != 0) {
            randX = (int)(Math.random() * 20);
            randY = (int)(Math.random() * 20);
        }
        flu.add(new Flu(randX, randY));//creates a new flu object and adds it to array of flu
        gridData[flu.get(flu.size()-1).getX()][flu.get(flu.size()-1).getY()] = 1;//adds a flu to the grid
        aliveFlu++;//adds to the flu counter
    }
    @FXML
    private void handlebtnHiv() {
        hivExist = true;
        int randX = (int)(Math.random() * 20);//will continue to find a random valid spot to place a hiv until one is found
        int randY = (int)(Math.random() * 20);
        while (gridData[randX][randY] != 0) {
            randX = (int)(Math.random() * 20);
            randY = (int)(Math.random() * 20);
        }
        hiv.add(new Hiv(randX, randY));//creates a new hiv object and adds it to array of hiv
        gridData[hiv.get(hiv.size()-1).getX()][hiv.get(hiv.size()-1).getY()] = 2;//adds hiv to grid
        aliveHiv++;//adds to hiv counter
    }
    @FXML
    private void handlebtnCord() {
        int randX = (int)(Math.random() * 20);//will continue to find a random valid spot to place a cprdyceps until one is found
        int randY = (int)(Math.random() * 20);
        while (gridData[randX][randY] != 0) {
            randX = (int)(Math.random() * 20);
            randY = (int)(Math.random() * 20);
        }
        cordyceps.add(new Cordyceps(randX, randY));//creates a new cordyceps object and adds it to array of cordyceps
        gridData[cordyceps.get(cordyceps.size()-1).getX()][cordyceps.get(cordyceps.size()-1).getY()] = 3;//adds cordycpes to grid
        aliveCordyceps++;//adds to cordyceps counter
    }
    @FXML
    private void handlebtnCoro() {
        coronaExist = true;
        int randX = (int)(Math.random() * 20);//will continue to find a random valid spot to place a coronavirus until one is found
        int randY = (int)(Math.random() * 20);
        while (gridData[randX][randY] != 0) {
            randX = (int)(Math.random() * 20);
            randY = (int)(Math.random() * 20);
        }
        coronavirus.add(new Coronavirus(randX, randY));//creates a new coronavirus object and adds it to array of coronavirus
        gridData[coronavirus.get(coronavirus.size()-1).getX()][coronavirus.get(coronavirus.size()-1).getY()] = 4;//adds coronavirus to grid
        aliveCoronavirus++;//adds to coronavirus counter
    }
    @FXML
    private void handlestopFlu() {
        stopFlu = true;
    }
    @FXML
    private void handlestopHiv() {
        stopHiv = true;
    }
    @FXML
    private void handlestopCoronavirus() {
        stopCoronavirus = true;
    }
    @FXML
    private void handlestopCordyceps() {
        stopCordyceps = true;
    }
    @FXML
    private void handlebtnReset() {
        sliderTemp.setValue(50.0);//sets starting humidity and temperature at the middle
        sliderHumid.setValue(50.0);
        check1.setSelected(true);
        for(int i=0; i<20; i++){
            for(int j=0; j<20;j++){
                btn[i][j].setStyle("-fx-background-color:#" + colors.get(5) + "; -fx-background-radius: 0;");
                gridData[i][j]= 0;
            }
        }
        flu.clear();
        hiv.clear();
        coronavirus.clear();
        cordyceps.clear();
        fluExist = false;
        hivExist = false;
        coronaExist = false;
        stopFlu = false;
        stopHiv = false;
        stopCoronavirus = false;
        stopCordyceps = false;
        aliveFlu = 0;
        aliveHiv = 0;
        aliveCordyceps = 0;
        aliveCoronavirus = 0;
        deathFlu = 0;
        deathHiv = 0;
        deathCordyceps = 0;
        deathCoronavirus = 0;
        barGraph.getData().clear();
        lst1.getItems().clear();
    }
    public void updateScreen(){
        aliveFlu = 0;
        aliveHiv = 0;
        aliveCordyceps = 0;
        aliveCoronavirus = 0;
        for(int i=0; i<btn.length; i++) {
            for (int j = 0; j < btn.length; j++) {
                if (gridData[i][j] == 1){//draws a flu on the grid
                    aliveFlu++;//adds to flu counter
                    btn[i][j].setStyle("-fx-background-color:#330066; -fx-background-radius: " + (10 - (int)(sliderHumid.getValue() / 10)) + ";");
                }
                else if (gridData[i][j] == 2 && !stopHiv){//draws a hiv on the grid if there has been no cure for hiv
                    aliveHiv++;//adds to hiv counter
                    btn[i][j].setStyle("-fx-background-color:#e6e600; -fx-background-radius: " + (10 - (int)(sliderHumid.getValue() / 10)) + ";");
                }
                else if (gridData[i][j] == 3 && !stopCordyceps){//draws a cordyceps on the grid if there has been no cure for cordyceps
                    aliveCordyceps++;//adds to cordyceps counter
                    btn[i][j].setStyle("-fx-background-color:#654321; -fx-background-radius: " + (10 - (int)(sliderHumid.getValue() / 10)) + ";");
                }
                else if (gridData[i][j] == 4){//draws a coronavirus on the grid
                    aliveCoronavirus++;//adds to coronavirus counter
                    btn[i][j].setStyle("-fx-background-color:#009933; -fx-background-radius: " + (10 - (int)(sliderHumid.getValue() / 10)) + ";");
                }
                else {//empty space so draws the terrain
                    btn[i][j].setStyle("-fx-background-color:#" + (colors.get((int)(sliderTemp.getValue() / 10))) + "; -fx-background-radius: " + (10 - (int)(sliderHumid.getValue() / 10)) + ";");
                }
            }
        }
        updateList();
    }
    public void start() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                List<Flu> newFlu = new ArrayList<>();//these lists will keep track of the new and dead diseases that will be added to the list of existing diseases
                List<Flu> deadFlu = new ArrayList<>();
                List<Hiv> newHiv = new ArrayList<>();
                List<Hiv> deadHiv = new ArrayList<>();
                List<Cordyceps> newCordyceps = new ArrayList<>();
                List<Cordyceps> deadCordyceps = new ArrayList<>();
                List<Coronavirus> newCoronavirus = new ArrayList<>();
                List<Coronavirus> deadCoronavirus = new ArrayList<>();
                double humidProb = 0.5 + (sliderHumid.getValue()/250);//calculates change in probability based on humidity, the higher the humidity, the higher the probability
                double tempSpeed = (Math.abs(50 - sliderTemp.getValue()) * 100000000.0) / 2;//calculates change in speed based on temperature, more extreme temperatures mean larger time values
                if(flu.size()>0){
                    for (Flu x : flu){
                        if (now - x.getMoveTime() > 2000000000.0 + tempSpeed){//more extreme
                            if (Math.random() > 0.7) {//there is a large chance that the flu will not move
                                x.moveDisease(gridData, 1);
                            }
                            x.resetMoveTime();
                        }
                        if (now - x.getSpreadTime() > 4000000000.0 + tempSpeed){//flu will spread
                            if (stopFlu) {//if there is treatment for flu the the probability of it spreading will decrease
                                addList(newFlu, x.spreadFlu(gridData, humidProb - 0.35));//probability of spreading drops by 35%, adds newly created flu to array of new flu
                            }
                            else {
                                addList(newFlu, x.spreadFlu(gridData, humidProb + 0.05));//if there is no better treatment, flu will spread normally
                            }
                            x.resetSpreadTime();
                        }
                        if (now - x.getAliveTime() > 10000000000.0 - tempSpeed) {//more extreme temperatures will cause flu to die quicker, existing flu treatment causes flu to live shorter
                            x.diseaseDead(gridData, x.getX(), x.getY());//kills the disease
                            deadFlu.add(x);//adds the flu to the list of dead flu
                        }
                        if (now - x.getFightTime() > 10000000000.0 + tempSpeed) {//more extreme temperatures will mean flu will fight other diseases less
                            countDeath(x.fightDisease(gridData, 1));//counts the disease that died in the fight
                            x.resetFightTime();
                        }
                    }
                    deathFlu += deadFlu.size();
                    addList(flu, newFlu);//adds all the new flu that were created to the list of current flu
                    removeList(flu, deadFlu);//removes all the flu that died from the list of current flu
                }
                if(hiv.size()>0){
                    for (Hiv y : hiv) {
                        if (now - y.getMoveTime() > 1000000000.0) {//hiv is not affected by temperature or humidity
                            if (Math.random() > 0.5) {//large chance hiv will not move
                                y.moveDisease(gridData, 2);//moves the hiv
                            }
                            y.resetMoveTime();
                        }
                        if (now - y.getSpreadTime() > 10000000000.0) {
                            addList(newHiv, y.spreadHiv(gridData));//adds newly created hiv to array of new hiv
                            y.resetSpreadTime();
                        }
                        if (now - y.getAliveTime() > 30000000000.0) {//since there is no proper cure for hiv, the disease lives long
                            y.diseaseDead(gridData, y.getX(), y.getY());//kills the hiv
                            deadHiv.add(y);//adds the dead hiv to list of dead hiv
                        }
                        if (now - y.getFightTime() > 10000000000.0) {//hiv will fight other diseases
                            countDeath(y.fightDisease(gridData, 2));//counts the disease that died in fight
                            y.resetFightTime();
                        }
                    }
                    deathHiv += deadHiv.size();
                    addList(hiv, newHiv);//adds the newly created hiv to list of existing hiv
                    removeList(hiv, deadHiv);//removes dead hiv from list of existing hiv
                }
                if(cordyceps.size()>0){
                    for (Cordyceps z : cordyceps) {
                        if (now - z.getMoveTime() > 500000000.0) {//cordyceps is not affected by temperature or humidity
                            if (fluExist || hivExist || coronaExist) {//as long as there are other diseases on the grid, the cordyceps will target them
                                z.moveCordyceps(gridData);
                            }
                            else {
                                z.moveDisease(gridData, 3);//if there are no other diseases, the cordyceps moves randomly
                            }
                            z.resetMoveTime();
                        }
                        if (now - z.getSpreadTime() > 5000000000.0) {//cordyceps spreads fast as it is a zombie infection
                            addList(newCordyceps, z.spreadCordyceps(gridData));//adds newly created cordyceps to list of new cordyceps
                            z.resetSpreadTime();
                        }
                        if (now - z.getAliveTime() > 10000000000.0) {//cordyceps will live for very long as there is no treatment
                            z.diseaseDead(gridData, z.getX(), z.getY());//kills the cordyceps
                            deadCordyceps.add(z);//adds dead cordyceps to the list of dead cordyceps
                        }
                        if (now - z.getFightTime() > 1000000000.0) {//cordyceps will fight other diseases very frequently
                            countDeath(z.fightDisease(gridData, 3));//counts the disease that died in fight
                            z.resetFightTime();
                        }
                    }
                    deathCordyceps += deadCordyceps.size();
                    addList(cordyceps, newCordyceps);//adds newly created cordyceps to list of existing cordyceps
                    removeList(cordyceps, deadCordyceps);//removes dead cordyceps from list of existing cordyceps
                }
                if(coronavirus.size()>0){
                    for (Coronavirus a : coronavirus){
                        if (now - a.getMoveTime() > 3000000000.0 + tempSpeed){//coronavirus is affected by temperature and humidity, and will move slower in extreme temperatures
                            if (Math.random() > 0.6) {//chance the coronavirus will not move
                                a.moveDisease(gridData, 4);
                            }
                            a.resetMoveTime();
                        }
                        if (now - a.getSpreadTime() > 5000000000.0 + tempSpeed){//coronavirus will spread less in extreme temperatures
                            if (stopFlu) {
                                addList(newCoronavirus, a.spreadCoronavirus(gridData, humidProb - 0.4));//if there is a cure, the probability of spreading decreases by 40%
                            }
                            else {
                                addList(newCoronavirus, a.spreadCoronavirus(gridData, humidProb));//if there is no cure, the coronavirus will spread normally, with higher humidity meaning higher probability of spreading
                            }
                            a.resetSpreadTime();
                        }
                        if (now - a.getAliveTime() > 15000000000.0 - tempSpeed) {//the more extreme temperatures mean coronavirus dies quicker
                            a.diseaseDead(gridData, a.getX(), a.getY());//kills the coronavirus
                            deadCoronavirus.add(a);//adds dead coronavirus to list of dead coronavirus
                        }
                        if (now - a.getFightTime() > 20000000000.0 + tempSpeed) {//extreme temperatures mean coronavirus will take longer to fight
                            countDeath(a.fightDisease(gridData, 4));//counts the diseases the died in the fight
                            a.resetFightTime();
                        }
                    }
                    deathCoronavirus += deadCoronavirus.size();
                    addList(coronavirus, newCoronavirus);//adds the newly created coronavirus to list of existing coronavirus
                    removeList(coronavirus, deadCoronavirus);//removes dead coronavirus from list of existing coronavirus
                }
                if (graphTime > 0) {
                    if (now - graphTime > 1000000000.0) {
                        graphData(false);
                        graphTime = System.nanoTime();
                    }
                }
                updateScreen();
            }
        }.start();
    }
    public void countDeath(int d) {//determines which disease died in the fight and adds to the respective dead diseases counter
        if (d == 1) {
            deathFlu++;
        }
        else if (d == 2) {
            deathHiv++;
        }
        else if (d == 3) {
            deathCordyceps++;
        }
        else if (d == 4) {
            deathCoronavirus++;
        }
    }
    public void addList(List x, List y) {//adds the contents of one list to the other
        for (Object o : y) {
            x.add(o);
        }
    }
    public void removeList(List x, List y) {//removes the contents of one list from the other
        for (Object o : y) {
            x.remove(o);
        }
    }
}
