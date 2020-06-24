package ru.alfa.task3.services;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.alfa.task3.dto.DataTimeForModel;
import ru.alfa.task3.entity.QueueLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;

@Service
public class DataAnalystService {
    private HashMap<DataTimeForModel, List<Double>> data;
    private HashMap<DataTimeForModel, Long> model;
    private QueueLogService queueLogService;

    @Autowired
    public DataAnalystService(QueueLogService queueLogService) {
        this.queueLogService = queueLogService;
        this.data = new HashMap<>();
        this.model = new HashMap<>();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createModel() {
        List<QueueLog> queueLogs = queueLogService.findAll();

        queueLogs.stream().forEach(queueLog ->{
            addElement(getKeyByLog(queueLog), (double) SECONDS.between(queueLog.getStartTimeOfWait(), queueLog.getEndTimeOfWait() ) );
        });

        for(HashMap.Entry<DataTimeForModel, List<Double>> entry : data.entrySet()) {
            model.put(entry.getKey(), Math.round(getMedian(entry.getValue())) );
        }

    }

    public DataTimeForModel getKeyByLog(QueueLog queueLog) {
        return new DataTimeForModel(queueLog.getBranches().getId(),
                queueLog.getData().getDayOfWeek().getValue(),
                queueLog.getEndTimeOfWait().getHour());

    }

    private void addElement(DataTimeForModel key, Double element){
        if ((data != null || data.containsKey(key)) && data.get(key) != null){
            data.get(key).add(element);
        }else{
            List<Double> elements = new ArrayList<>();
            elements.add(element);
            data.put(key, elements);
        }
    }

    private double getMedian(List<Double> values){
        Median median = new Median();
        return median.evaluate(convertDoubles(values));
    }

    public static double[] convertDoubles(List<Double> doubles)
    {
        double[] ret = new double[doubles.size()];
        for (int i = 0; i < ret.length; i++)
        {
            ret[i] = doubles.get(i);
        }
        return ret;
    }

    public HashMap<DataTimeForModel, Long> getModel() {
        return model;
    }

}
