/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package acceptance.uk.gov.hmrc.apidocumentation

import org.openqa.selenium.support.ui.{ExpectedCondition, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalatest.concurrent.{Eventually, IntegrationPatience}
import org.scalatest.selenium.WebBrowser
import org.scalatest.selenium.WebBrowser.{go => goo}
import org.scalatest.{Assertions, Matchers}


trait NavigationSugar extends WebBrowser with Eventually with Assertions with Matchers with IntegrationPatience with Wait {

  def goOn(page: WebPage)(implicit webDriver: WebDriver) {
    go(page)
    on(page)
  }

  def go(page: WebPage)(implicit webDriver: WebDriver) {
    goo to page
  }

  def goToUrl(url: String)(implicit webDriver: WebDriver) {
    goo to url
  }

  def on(page: WebPage)(implicit webDriver: WebDriver) {
    eventually {
      webDriver.findElement(By.tagName("body"))
      page.isCurrentPage
    }
    assert(page.isCurrentPage, s"Page was not loaded: $page (${page.url}), current Url: $currentUrl")
  }

  def loadPage(timeout: Int = 30)(implicit webDriver: WebDriver) = {
    val wait = new WebDriverWait(webDriver, timeout)
    wait.until(
      new ExpectedCondition[WebElement] {
        override def apply(d: WebDriver) = d.findElement(By.tagName("body"))
      }
    )
  }

  def anotherTabIsOpened()(implicit webDriver: WebDriver) {
    webDriver.getWindowHandles.size() should be(2)
  }

  def browserGoBack()(implicit webDriver: WebDriver) {
    webDriver.navigate().back()
  }

  def browserGoForward()(implicit webDriver: WebDriver) {
    webDriver.navigate().forward()
  }

  def checkPageTitle(expectedPageTitle : String)(implicit webDriver: WebDriver): Unit = {
    webDriver.getTitle shouldBe expectedPageTitle
  }
}
