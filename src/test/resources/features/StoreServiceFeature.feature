Feature: Managing Warehouse with Multiple Sales Checks

  Scenario: Adding a check and verifying products amount in warehouse change
    Given the following products exist in warehouse:
      | Product  | Initial Amount |
      | Bounty   | 50             |
      | Snickers | 10             |
    When I create check with the following sale requests:
      | Sale Requests |
      | Bounty:10     |
      | Snickers:5    |
    Then the warehouse should have the following amounts:
      | Product  | Expected Amount |
      | Bounty   | 40              |
      | Snickers | 5               |

  Scenario Outline: Adding multiple checks and verifying products amount in warehouse change
    Given the following products exist in warehouse:
      | Product   | Initial Amount |
      | <product1> | <initial1>    |
      | <product2> | <initial2>    |

    When I create check with the following sale requests:
      | Sale Requests |
      | <sale_requests> |

    Then the warehouse should have the following amounts:
      | Product   | Expected Amount |
      | <product1> | <expected1>    |
      | <product2> | <expected2>    |

    Examples:
      | product1  | initial1 | product2  | initial2 | sale_requests                               | expected1 | expected2 |
      | Bounty    | 50       | Snickers  | 10       | Bounty:10, Snickers:5, Bounty:20, Bounty:20 | 0         | 5         |
      | Mars      | 30       | KitKat    | 20       | Mars:15, Mars:5                             | 10        | 20        |
      | Twix      | 25       | Milky Way | 15       | Milky Way:6, Twix:20, Milky Way:7           | 5         | 2         |
