Feature: Managing Warehouse with Multiple Sales Checks

  Scenario: Adding a check and verifying products amount in warehouse change
    Given the following products exist in warehouse:
      | Product  | Cost | Initial Amount |
      | Bounty   | 10.0 | 100            |
      | Snickers | 5.0  | 60             |
      | Twix     | 15.0 | 40             |
      | MilkyWay | 5.0  | 80             |
      | Kinder   | 50.0 | 50             |
    When I create check with the following sale requests:
      | Product    | Amount |
      | Bounty     | 10     |
      | Snickers   | 60     |
      | Twix       | 15     |
    Then the warehouse should have the following amounts:
      | Product  | Expected Amount |
      | Bounty   | 90              |
      | Snickers | 0               |
      | Twix     | 25              |
      | MilkyWay | 80              |
      | Kinder   | 50              |

    And check totalCost should be 625.0


  Scenario Outline: Adding checks and verifying products amount in warehouse change
    Given the following products exist in warehouse:
      | Product    | Cost    | Initial Amount |
      | <product>  | <cost>  | <initial>      |

    When I create check with the following sale requests:
      | Product    | Amount |
      | <product> | <sale_amount> |

    Then the warehouse should have the following amounts:
      | Product   | Expected Amount |
      | <product> | <expected>      |

    And check totalCost should be <total>

    Examples:
      | product   | cost | initial | sale_amount | expected | total  |
      | Bounty    | 20.0 | 50      | 50          | 0        | 1000.0 |
      | Snickers  | 19.0 | 10      | 5           | 5        | 95.0   |
      | Mars      | 10.0 | 30      | 20          | 10       | 200.0  |
      | KitKat    | 15.0 | 20      | 0           | 20       | 0.0    |
      | Twix      | 18.0 | 15      | 10          | 5        | 180.0  |
      | Milky Way | 8.0  | 15      | 13          | 2        | 104.0  |
