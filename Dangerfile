# Avoid issuing warnings about things that exist before the pull request.
github.dismiss_out_of_range_messages

# Issue a warning if a pull request has WIP (Work in Progress) in the title.
warn('PR is classed as Work in Progress') if github.pr_title.include? '[WIP]'

# unit testing
#junit_tests_dir = "path/to/your/results/**/*.xml"
#Dir[junit_tests_dir].each do |file_name|
#  junit.parse file_name
#  junit.show_skipped_tests = true
#  junit.report
#end

# android lints
android_lint.report_file = "build/reports/*.xml"
android_lint.filtering = true
android_lint.skip_gradle_task = true
android_lint.lint(inline_mode: true)

# ktlint
#checkstyle_format.base_path = Dir.pwd
#checkstyle_format.report = "build/ktlint-report.xml"