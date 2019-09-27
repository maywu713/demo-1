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
public class HomeController {
	Object allData = "sss";
	String allDataHtml = "<table class=\"table is-bordered is-striped is-narrow is-hoverable is-fullwidth\"><tr><th>date</th><th>CO</th><th>NO2</th></tr>";
	
	@RequestMapping("home")
	public String home() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
		
		
		InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
		String dbName = "airquality";
		influxDB.query(new Query("CREATE DATABASE " + dbName));
		influxDB.setDatabase(dbName);
		String rpName = "aRetentionPolicy";
		influxDB.query(new Query("CREATE RETENTION POLICY " + rpName + " ON " + dbName + " DURATION INF REPLICATION 2 SHARD DURATION 7d DEFAULT"));
		influxDB.setRetentionPolicy(rpName);
		influxDB.enableBatch(BatchOptions.DEFAULTS.exceptionHandler(
		        (failedPoints, throwable) -> { 
		        throwable.printStackTrace();
		        })
		);

		
		List<String> list = CsvReader.init();
		
		for (int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			 String[] fields = line.split(";|,");

	    	 if (fields.length > 0) {   
	    		 try {
	    			String joinedDate = fields[0].concat(" ").concat(fields[1]);
					Date date = sdf.parse(joinedDate);
					long longDate = date.getTime();
					influxDB.write(Point.measurement("test")
	    				    .time(longDate, TimeUnit.MILLISECONDS)
	    				    .addField("co", Double.valueOf(fields[2]))
	    				    .addField("no2", Double.valueOf(fields[9]))
	    				    // add the remaining fields
	    				    .build());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		 
	    	 }
		}
		

		Query query = new Query("select COUNT(co) from test where time < 1104537600000000000 AND time > 1072915200000000000", dbName);
		influxDB.query(query, result -> {
			System.out.println(result);
		}, throwable -> {
			
		});
		
		Query query2 = new Query("select COUNT(no2) from test where time < 1104537600000000000 AND time > 1072915200000000000", dbName);
		influxDB.query(query2, result -> {
			System.out.println(result);
		}, throwable -> {
			
		});
		

//
		influxDB.close();
		
		
		System.out.println("hey");
		
		return "home.jsp";
	}
	
	public String getAllData() {
		InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
		String dbName = "airquality";
		Query query3 = new Query("select * from test", dbName);
		QueryResult data;

		data = influxDB.query(query3);

		influxDB.close();


		data.getResults().get(0).getSeries().get(0).getValues().subList(0, 100).forEach(serie -> {
			this.allDataHtml += "<tr>";
			serie.forEach(field -> {
				this.allDataHtml += "<td>";
				this.allDataHtml += field;
				this.allDataHtml += "</td>";
			});
			this.allDataHtml += "</tr>";
		});
		
		return this.allDataHtml;
	}
	

}
