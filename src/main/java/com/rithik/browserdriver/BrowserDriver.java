package com.rithik.browserdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class BrowserDriver {
    WebDriver driver;
    WebDriverWait wait;

    WebElement chatBox;

    private boolean stop = false;

    private Thread beg;

    public BrowserDriver() {
        driver = new OperaDriver();
        wait = new WebDriverWait(driver, 10);

        beg = new Thread(() -> {
            while (!stop) {
                sendMessage("pls beg");
                try {
                    Thread.sleep(47000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void startBegging() {
        beg.start();
    }

    public void stopDriver() {
        stop = true;
        driver.quit();
    }

    public boolean login(int attempts, String email, String password) {
        boolean errStatus = false;
        boolean loggedIn = false;

        for (int attempted = 0; (attempted < attempts) && !loggedIn; attempted++) {
            driver.get("https://discord.com/app");
            try {
                WebElement loginBox;

                try {
                    loginBox = driver.findElement(By.xpath("//*[@id=\"app-mount\"]/div[2]/div/div[2]/div/div/form"));
                } catch (NoSuchElementException e) {
                    System.out.println("Failed to find login box...");
                    errStatus = true;
                    break;
                } catch (Exception e) {
                    System.out.println(e.toString());
                    errStatus = true;
                    break;
                }

                try {
                    loginBox.findElement(By.name("email")).sendKeys(email);
                    loginBox.findElement(By.name("password")).sendKeys(password);
                } catch (Exception e) {
                    System.out.println("Couldn't enter email or pass, " + e.toString());
                    errStatus = true;
                    break;
                }

                try {
                    List<WebElement> buttonList = loginBox.findElements(By.className("contents-18-Yxp"));
                    for (WebElement button : buttonList) {
                        if (button.getText().equalsIgnoreCase("login")) {
                            button.click();
                            loggedIn = true;
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                    errStatus = true;
                    break;
                }


            } catch (Exception e) {
                System.out.println(e.toString() + " in outer catch");
                errStatus = true;
            }

            if (errStatus)
                break;
        }

        if (loggedIn) {
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[1]/video")));
            wait.until(ExpectedConditions
                    .invisibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[1]/video")));
        }

        return !errStatus;
    }

    public void selectServerChannel(String channelUrl) {
        driver.get(channelUrl);

        chatBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div/div[2]/div/div/div/div/div[2]/div/main/form/div/div/div/div/div[3]/div[2]")));
    }

    public boolean sendMessage(String message) {
        boolean errStatus = false;

        try {
            chatBox.click();
            chatBox.sendKeys(message + Keys.ENTER);

        } catch (Exception e) {
            e.printStackTrace();
            errStatus = true;
        }

        return !errStatus;
    }
}
