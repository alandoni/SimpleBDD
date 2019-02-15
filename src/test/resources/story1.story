StoryName: First test

Scenario: First Scenario on First Test
!-- this is a comment
@Meta smoke
@id 123
@platform windows
@only
Given this method works
When we call this one
Then we assert this

Scenario: Second Scenario on First Test
@Meta sanity
@id 456
@platform unix
Given this method works
When we print the text <methodOne>
Then we assert this is <methodOne>

Scenario: Third Scenario on First Test
@Meta sanity
@id 456
@platform unix
Given this method works
When we print the text <"method, One",methodTwo>
Then we assert this is <methodOne>

Scenario: Third Scenario on First Test
@Meta sanity
@id 456
@platform unix
Given this method works
When we print the text <"method, One",methodTwo> but <methodThree>
Then we assert this is <methodOne>