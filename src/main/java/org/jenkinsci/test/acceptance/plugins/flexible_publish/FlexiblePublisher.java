package org.jenkinsci.test.acceptance.plugins.flexible_publish;

import java.util.function.Consumer;

import org.jenkinsci.test.acceptance.po.AbstractStep;
import org.jenkinsci.test.acceptance.po.Describable;
import org.jenkinsci.test.acceptance.po.Job;
import org.jenkinsci.test.acceptance.po.PostBuildStep;
import org.jenkinsci.test.acceptance.po.Step;

/**
 * Page object for the FlexiblePublish Plugin.
 *
 * @author Andreas Neumeier
 */
@Describable("Flexible publish")
public class FlexiblePublisher extends AbstractStep implements PostBuildStep {

    /**
     * Creates a new page object.
     *
     * @param parent
     *         parent page object
     * @param path
     *         path on the parent page
     */
    public FlexiblePublisher(final Job parent, final String path) {
        super(parent, path);
    }

    /**
     * Adds the specified publisher to this job. Publishers are stored in a list member to provide
     * later access for modification.
     *
     * @param publisherClass the publisher to configure
     * @param <T>            the type of the publisher
     */
    public <T extends PostBuildStep> T addPublisher(Class<T> publisherClass) {
        T p = addStep(publisherClass);
        //publishers.add(p);
        return p;
    }

    /**
     * Adds the specified publisher to this job. Publishers are stored in a list member to provide
     * later access for modification. After the publisher has been added the publisher is configured
     * with the specified configuration lambda. Afterwards, the job configuration page still is visible and
     * not saved.
     *
     * @param type          the publisher to configure
     * @param configuration the additional configuration options for this job
     * @param <T>           the type of the publisher
     */
    public <T extends PostBuildStep> T addPublisher(final Class<T> type, final Consumer<T> configuration) {
        T p = addPublisher(type);
        //configuration.accept(p);
        return p;
    }

    private <T extends Step> T addStep(final Class<T> type) {
        // TODO: Ensure tat configuration page is opened
        //ensureConfigPage();

        String path = createPageArea("publishers/publisherList", new Runnable() {
            @Override public void run() {
                control(by.path("/publisher[FlexiblePublisher]/publishers/hetero-list-add[publisherList]")).selectDropdownMenu(type);
            }
        });

        // TODO: Currently returns null because flex publish publishers are constructed differently
        //return newInstance(type, this, path);
        return null;
    }
}
