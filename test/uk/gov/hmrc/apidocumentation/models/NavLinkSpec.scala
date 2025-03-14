/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.apidocumentation.models

import uk.gov.hmrc.play.test.UnitSpec

class NavLinkSpec extends UnitSpec {

  "NavigationHelper" should {

    "return static navlinks for devhub" in {
      StaticNavLinks() shouldBe
        Seq(
          NavLink("Documentation", "/api-documentation/docs/using-the-hub"),
          NavLink("Applications", "/developer/applications"),
          NavLink("Support", "/developer/support"),
          NavLink("Service availability", "https://api-platform-status.production.tax.service.gov.uk/", openInNewWindow = true))
    }
  }
}
