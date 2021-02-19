# Remarks?

I'm not sure how the rounds for the war should be counted, so I'm only counting cards played during the war and not the
initial cards that started a war.

Also I'm NOT printing the cards that started the war.

Didn't implement the multi language stuff :(

I'm also aware of a scenario when the code breaks in GamePlay line 112 due to ArrayIndexOutOfBoundsException but I
haven't been able to reproduce it, so I'm afraid to attempt a fix and break the rest of the code.

Wasn't sure where to place the .log file so I'm just saving it in the same folder where the jar or code is being run.

## Instructions

### Run

1. `mvn package`
2. `java -jar target/War.jar 4 small`

OR if we want to start with a ssc.json file

`java -jar target/War.jar`

### Test

`mvn test`