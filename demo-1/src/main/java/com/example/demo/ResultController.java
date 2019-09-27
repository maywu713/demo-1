package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.InfluxDB.LogLevel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResultController {
	String means = "";
	
	@RequestMapping("result")
	public String home() {
		
		
		return "result.jsp";
	}
	
	public String getMeans() {
		InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
		String dbName = "airquality";
		
		QueryResult data1;
		QueryResult data2;
		
		Query query = new Query("select MEAN(co) from test where time < 1104537600000000000 AND time > 1072915200000000000", dbName);
		Query query2 = new Query("select MEAN(no2) from test where time < 1104537600000000000 AND time > 1072915200000000000", dbName);
		
		data1 = influxDB.query(query);
		data2 = influxDB.query(query2);
		
		data1.getResults().get(0).getSeries().get(0).getValues().forEach(serie -> {
			this.means += "<p>Mean CO level throughout year 2014: <u>";
			this.means += serie.get(1);
			this.means += "</u></p>";
		});
		data2.getResults().get(0).getSeries().get(0).getValues().forEach(serie -> {
			this.means += "<p>Mean NO2 level throughout year 2014: <u>";
			this.means += serie.get(1);
			this.means += "</u></p>";
		});

		System.out.println("results: " + this.means);

//
		influxDB.close();
		
		return this.means;
	}
	

}
