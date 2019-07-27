package io.github.handofgod94;

import io.github.handofgod94.domain.Badge;
import io.github.handofgod94.service.BadgeGenerationService;
import io.vavr.control.Option;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

/**
 * Goal to generate badge during the build.
 *
 * <p>Default Phase: Verify
 */
@Mojo(name = "badge", defaultPhase = LifecyclePhase.VERIFY)
public class MyMojo extends AbstractMojo {

  @Parameter(property = "badge.badgeLabel", defaultValue = "coverage")
  private String badgeLabel;

  @Parameter(property = "badge.jacocoReportLocation",
      defaultValue = "${project.reporting.outputDirectory}/jacoco/jacoco.csv")
  private File jacocoReportFile;

  @Parameter(property = "badge.outputFile",
      defaultValue = "${project.build.directory}/coverage.svg")
  private File outputFile;

  @Parameter(property = "badge.coverageCategory", defaultValue = "INSTRUCTION")
  private Badge.CoverageCategory coverageCategory;

  @Override
  public void execute() {
    BadgeGenerationService generationService =
        new BadgeGenerationService(coverageCategory, badgeLabel, jacocoReportFile, outputFile);
    Option<Badge> badge = generationService.generate();

    String buildMessage = Match(badge).of(
        Case($Some($()), b -> String.format("Total Coverage calculated by badge plugin: %s",
                                            b.getBadgeValue())),
        Case($None(), () -> "Could not create badge, please verify config")
    );

    getLog().info(buildMessage);
  }

}
