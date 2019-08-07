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

import java.util.concurrent.TimeUnit

import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait}
import org.openqa.selenium._
import org.scalatest.concurrent.Eventually
import org.scalatest.concurrent.PatienceConfiguration.Timeout

import scala.concurrent.duration._

trait Wait extends Eventually {

  override implicit val patienceConfig: PatienceConfig = PatienceConfig(timeout = 2 seconds)
  val timeout = Timeout(patienceConfig.timeout)
  val fluentWaitPollingMilliseconds = 500

  def waitForElement(by: By, timeout: Int = 5): WebElement = {
    val wait = new FluentWait[WebDriver](Env.driver)
      .withTimeout(timeout, TimeUnit.SECONDS)
      .pollingEvery(fluentWaitPollingMilliseconds, TimeUnit.MILLISECONDS)
      .ignoring(classOf[NoSuchElementException], classOf[StaleElementReferenceException])
    wait.until(ExpectedConditions.visibilityOfElementLocated(by))
  }

  def waitForPageToReload(oldPageElement: WebElement): Unit = {
    eventually(timeout) {
      try {
        oldPageElement.getText
        throw new RuntimeException("Old element still present")
      }
      catch {
        case _: StaleElementReferenceException => Unit
      }
    }
  }
}
