package GUI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

// Tracé de la distribution des retours du portefeuille, à superposer avec le kernel fitting (à réaliser)

public class returnGUI extends ApplicationFrame {

	private static final long serialVersionUID = 1L;

	// création de la fenêtre
    public returnGUI(final String title, double[] portfolioReturns, int numOfBins) {
        super(title);
        JFreeChart chart = createHistogram(portfolioReturns, numOfBins);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }
    
    // Ce que contient la fenêtre (un histogramme + une série (x,y) qui correspondra aux points de la fonction de densité estimée)
    public static JFreeChart createHistogram(double[] values,int numOfBins){
    	  HistogramDataset dataset1=new HistogramDataset();
    	  final XYItemRenderer renderer1 = new XYBarRenderer(0.20);
    	  dataset1.setType(HistogramType.RELATIVE_FREQUENCY);
    	  dataset1.addSeries("",values,numOfBins);
    	 
    	  final ValueAxis domainAxis = new NumberAxis("log-returns");
          final ValueAxis rangeAxis = new NumberAxis("frequency");
          final XYPlot plot = new XYPlot(dataset1, domainAxis, rangeAxis, renderer1);
          
    	  XYSeriesCollection dataset2 = createDataset2();
    	  final XYItemRenderer renderer2 = new StandardXYItemRenderer();
    	  
    	  plot.setDataset(1, dataset2);
    	  plot.setRenderer(1, renderer2);
    	  plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

          // return a new chart containing the overlaid plot...
          return new JFreeChart("best portfolio log-returns for VaR 1 day", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    	}
    
    private static XYSeriesCollection createDataset2(){
    	final XYSeries serie = new XYSeries("");
    	/*serie.add(0,0.5);
    	serie.add(0.001,0.2);
    	serie.add(0.002,0.3);
    	serie.add(0.0017,0.4);*/
    	final XYSeriesCollection dataset = new XYSeriesCollection(serie);
        return dataset;
    }
}
