package plugins;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.google.inject.Inject;

import org.jenkinsci.test.acceptance.junit.AbstractJUnitTest;
import org.jenkinsci.test.acceptance.junit.WithPlugins;
import org.jenkinsci.test.acceptance.plugins.flexible_publish.FlexiblePublisher;
import org.jenkinsci.test.acceptance.plugins.warnings_ng.IssuesRecorder;
import org.jenkinsci.test.acceptance.plugins.warnings_ng.ScrollerUtil;
import org.jenkinsci.test.acceptance.po.FreeStyleJob;
import org.jenkinsci.test.acceptance.po.Slave;
import org.jenkinsci.test.acceptance.slave.LocalSlaveController;
import org.jenkinsci.test.acceptance.slave.SlaveController;
import org.jenkinsci.test.acceptance.utils.mail.MailService;

import static org.assertj.core.api.Assertions.*;

/**
 * Acceptance tests for the Flexible Publish Plugin.
 *
 * @author Andreas Neumeier
 */
@WithPlugins({"flexible-publish", "warnings-ng"})
public class FlexiblePublishPluginTest extends AbstractJUnitTest {
    private static final String FLEXIBLE_PUBLISH_PLUGIN_PREFIX = "/flexible_publish_plugin/";

    @Inject
    private MailService mail;

    @Test
    public void shouldSendTwoMails() {
        SlaveController controller = new LocalSlaveController();
        Slave agent;
        try {
            agent = controller.install(jenkins).get();
        }
        catch (InterruptedException | ExecutionException e) {
            throw new AssertionError("Error while getting slave.", e);
        }
        agent.configure();
        agent.setLabels("agent");
        agent.save();
        agent.waitUntilOnline();

        //mail.setup(jenkins);

        assertThat(agent.isOnline()).isTrue();
        FreeStyleJob job = createFreeStyleJob();
        job.configure();
        job.setLabelExpression(agent.getName());

        FlexiblePublisher publisher = job.addPublisher(FlexiblePublisher.class, p -> {
            p.addPublisher(IssuesRecorder.class);
        });

        System.out.println("test");
    }

    private FreeStyleJob createFreeStyleJob(final String... resourcesToCopy) {
        FreeStyleJob job = jenkins.getJobs().create(FreeStyleJob.class);
        ScrollerUtil.hideScrollerTabBar(driver);
        for (String resource : resourcesToCopy) {
            job.copyResource(FLEXIBLE_PUBLISH_PLUGIN_PREFIX + resource);
        }
        return job;
    }
}