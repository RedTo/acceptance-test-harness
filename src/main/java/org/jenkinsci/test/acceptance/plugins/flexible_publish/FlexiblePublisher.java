package org.jenkinsci.test.acceptance.plugins.flexible_publish;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.openqa.selenium.TimeoutException;

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

    private <T extends Step> T addStep(final Class<T> type) {
        // TODO: Ensure tat configuration page is opened
        //ensureConfigPage();

        // TODO: Ensure that publisher has been added and is shown
        //String path = createPageArea("/publisherList", new Runnable() {
        //    @Override public void run() {
        control(by.path("/publisher[FlexiblePublisher]/publishers/hetero-list-add[publisherList]")).selectDropdownMenu(type);
        //    }
        //});

        // TODO: Return the new publishers path not the path of the flex publisher
        return newInstance(type, this, getPath());
    }
}
