package com.java.sel.basic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.opencsv.CSVWriter;

public class ScrappingTask {

	static String name,address,phone,area,imageurl;
	static ChromeDriver driver;

	public void openBrowser() throws IOException {
		System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\Driver\\chrome\\chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get("https://downtowndallas.com/experience/stay/");
		driver.navigate().refresh();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		findDetails();
	}
	
	public void findDetails() throws IOException {
		driver.findElementByClassName("place-square__btn").click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name = driver.getTitle();
		WebElement imagepath = driver.findElementByXPath("/html/body/main/div/img"); 
		imageurl = imagepath.getAttribute("src");
		address = driver.findElementByPartialLinkText("Commerce").getText();
		phone = driver.findElementByLinkText("214-290-0111").getText();
		area = driver.findElementByLinkText("Main Street District").getText();
		outputSave();
	}
	
	public void outputSave() throws IOException {
		
		CSVWriter write= new CSVWriter(new FileWriter( "Imagedownload\\output.csv"));
		String set1[] = {"Name", "ImageUrl", "Address", "Phone", "Area"};
		String set2[] = {name, imageurl, address, phone, area};
		write.writeNext(set1);
		write.writeNext(set2);
		write.flush();
		
		URL openurl = new URL(imageurl);
		BufferedImage saveimage = ImageIO.read(openurl);
		ImageIO.write(saveimage, "png", new File("Imagedownload\\image.png"));
		
	}
	public static void main(String[] args) throws IOException  {
		
		ScrappingTask test = new ScrappingTask();
		test.openBrowser();
		driver.close();
		System.out.println("output generated in csv file and image saved");
		
	}

}
