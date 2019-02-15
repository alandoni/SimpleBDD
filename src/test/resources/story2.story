Scenario: First Scenario on Story2
Given this method works
When we print the texts <methodOne,methodTwo>
Then we assert this is <methodOne,methodTwo>

Scenario: Second Scenario on Story2
Given this method works
When we print the text <methodOne|methodTwo>
Then we assert this is <methodOne>

Scenario: Third Scenario on Story2
Given this method works
When we print the text <methodOne|methodTwo>
Then we assert this is <methodOne|methodTwo>