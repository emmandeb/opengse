// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.opengse.clienttests;

import org.junit.Test;

/**
 * Author: Mike Jennings
 * Date: Jan 30, 2009
 * Time: 3:24:23 PM
 */
public class ServletTestsWhichFailUnderJetty extends ServletTestsWhichConnectToARemoteServer {


  /**
   *  When the contentType attribute of the page  directive is specified, it will set the character
   *  encoding and MIME type in the response to the client.
   *  JavaServer Pages Specification v1.2, Sec. 2.10.1
   *
   * Strategy: Using the page directive, set the  contentType attribute to
   *    'text/plain;charset=ISO-8859-1'.
   * Verify on the client side that the Content-Type header was properly set in the  response.

   *
   * @throws Exception
   */
  @Test
  public void testJspXmlpositiveContentType()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion("/jsp-tests/jsp/core_syntax/directives/page/content/positiveContenttypeXML.jsp");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedContentType("text/plain");
    get.setExpectedContentTypeCharset("ISO-8859-1");
    get.connectToServerAndAssert();
  }


  /**
   * See XPoweredByHeaderTestlet for the expected header format.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testXPoweredByHeader()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion("/contextpath/XPoweredByHeaderTest");
    get.expectHeader("X-Powered-By");
    get.setExpectedResponseCode(200);
    get.connectToServerAndAssert();
  }

  /**
   * Test a pure wildcard servlet mapping will return request paths correctly.
   * E.g.
   * mapping: /* FooServlet
   * URL: /foo/bar
   * ServletPath: ""
   * Pathinfo: /foo/bar
   * <p/>
   * Note this also verifies that any user supplied /* mapping rule will take
   * precedence over the default /*mapping rule (which maps to the static file
   * servlet).
   * <p/>
   * TODO: the /* mapping required for this test interferes with the default
   * static file servlet. Need figure out a way how to test them together,
   * i.e. under the same context path.
   *
   * @throws Exception if anything goes wrong
   */
  @Test
  public void testRequestPathsUnderWildcardOnlyServletMapping()
      throws Exception {
    HttpRequestAsserter get = createGetAssertion("/contextpath/servletpath2/pathinfo");
    get.setExpectedContentType("text/plain");
    get.setExpectedResponseCode(200);
    get.setExpectedResponseLine("WildcardServletDispatchFilter.servletPath=");      // =""
    get.setExpectedResponseLine("WildcardServletDispatchFilter.pathInfo=/servletpath2/pathinfo");
    get.connectToServerAndAssert();
  }

}