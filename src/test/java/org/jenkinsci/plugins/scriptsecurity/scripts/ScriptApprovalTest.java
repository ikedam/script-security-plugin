/*
 * The MIT License
 *
 * Copyright 2014 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.scriptsecurity.scripts;

import org.jenkinsci.plugins.scriptsecurity.scripts.languages.GroovyLanguage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.jvnet.hudson.test.JenkinsRule;

public class ScriptApprovalTest {

    @Rule public JenkinsRule r = new JenkinsRule();

    @Test public void emptyScript() throws Exception {
        r.jenkins.setSecurityRealm(r.createDummySecurityRealm());
        configureAndUse("");
    }

    @Test public void noSecurity() throws Exception {
        configureAndUse("whatever");
    }

    @Test(expected=UnapprovedUsageException.class) public void withSecurity() throws Exception {
        r.jenkins.setSecurityRealm(r.createDummySecurityRealm());
        configureAndUse("whatever");
    }

    private void configureAndUse(String groovy) {
        ScriptApproval.get().configuring(groovy, GroovyLanguage.get(), ApprovalContext.create());
        assertEquals(groovy, ScriptApproval.get().using(groovy, GroovyLanguage.get()));
    }

}
