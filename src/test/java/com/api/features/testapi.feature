Feature: Pet Store API scenarios

  Scenario: Fetch and validate pet by id
     Given I set the base URI
    Then I find the pet by id "1"

  Scenario: Update the pet name and assert
       Given I set the base URI
       Then I update the pet name

   Scenario: Delete the pet name and assert
          Given I set the base URI
          Then I delete the pet "2"
