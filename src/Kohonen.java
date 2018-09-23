
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class Kohonen {

    int dimensi = 4;
    int map = 5;
    double[][] matrixBobot = new double[map][dimensi];
    
    Random rnd = new Random();
    double alpha = 0.5;
    double alphaReduction = 0.6;
    int maxIteration;
    ArrayList <Data> dataList = new ArrayList<Data>();
    String[][] matrixData;
    public Kohonen(int maxIteration, double alpha) throws IOException
    {
        this.maxIteration = maxIteration;
        this.alpha = alpha;
        for (int i = 0; i < this.map; i++) 
        {
            for (int j = 0; j < this.dimensi; j++) 
            {
                this.matrixBobot[i][j] = rnd.nextDouble();
            }
        }
        parseData("dataTxt.txt");
        learn();
        matrixData = new String[dataList.size()][dimensi+1];
        clusterData();
    }
    public Kohonen() throws IOException
    {
        this.maxIteration = 100;
        for (int i = 0; i < this.map; i++) 
        {
            for (int j = 0; j < this.dimensi; j++) 
            {
                this.matrixBobot[i][j] = rnd.nextDouble();
            }
        }
        parseData("dataTxt.txt");
        learn();
        matrixData = new String[dataList.size()][dimensi+1];
        clusterData();
    }

    public void updateBobot(int bestNeuron, double[] vectorData, int iteration) 
    {
        for (int i = 0; i < dimensi; i++) 
        {
            matrixBobot[bestNeuron][i] += getAlpha(iteration)*
                    (vectorData[i]-matrixBobot[bestNeuron][i]);
        }
    }
    
    public void parseData(String fileName) throws FileNotFoundException, IOException
    {
        String myLine;
        Data d;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        while((myLine = br.readLine()) != null)
        {
            String[] input = myLine.split(";");
            d = new Data(Integer.parseInt(input[0]),Integer.parseInt(input[1]),
                    Integer.parseInt(input[2]),input[3]);
            this.dataList.add(d);
        }
    }
    
    public double getDistance(double[] firstVector, double[] secondVector) 
    {
        double distance = 0;
        double x = 0, w = 0;
        double sum = 0;
        int weightSize = firstVector.length;

        if (weightSize != secondVector.length) {
            return -1;
        }

        for (int i = 0; i < weightSize; i++) {
            w = firstVector[i];
            x = secondVector[i];
            sum += (x - w) * (x - w);
        }

        distance = Math.sqrt(sum);
        return distance;
    }
    
    public double getAlpha(int iteration)
    {
        return this.alpha * Math.pow(this.alphaReduction, iteration);
    }
    
    public void setIteration(int i)
    {
        this.maxIteration = i;
    }
    
    public int findBestNeuron(double[] vectorData)
    {
        int bestNeuron = 0;
        double minJarak = Integer.MAX_VALUE;
        double jarak;
        double[] temp;
        for(int i=0;i<map;i++)
        {
            temp = this.matrixBobot[i];

            jarak = getDistance(temp, vectorData);
            if(jarak<minJarak)
            {
                minJarak = jarak;
                bestNeuron = i;
            }
        }
        return bestNeuron;
    }
    
    public void learn()
    {
        int bestNeuron = 0;
        int dataSize = this.dataList.size();
        double[] vectorData;
        for(int i=0;i<this.maxIteration;i++)
        {
            for(int j=0;j<dataSize;j++)
            {
                vectorData = this.dataList.get(j).getVector();
                bestNeuron = findBestNeuron(vectorData);
                
                updateBobot(bestNeuron, vectorData, i);
            }
        }
    }
    
    public int findHotelRate(Data d)
    {
        double[] vectorData = d.getVector();
        int cluster = findBestNeuron(vectorData);
        return cluster;
    }
    
    public void clusterData()
    {
        String[] vectorData;
        double[] vd;
        for(int i=0;i<dataList.size();i++)
        {
            vectorData = dataList.get(i).getData();
            vd = dataList.get(i).getVector();
            for(int j=0;j<5;j++)
            {
                if(j<=3)
                {
                    matrixData[i][j] = vectorData[j];
                }
                else
                {
                    matrixData[i][4] = findBestNeuron(vd)+1+"";
                }
            }
        }
    }
    
    public String[][] getClusteredData()
    {
        return this.matrixData;
    }
    
    public void setNewBobot(double[][] input)
    {
        this.matrixBobot = input;
    }
}
