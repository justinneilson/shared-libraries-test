/**
 * test.groovy
 */

void call(Map config = [:]) {
    String name = config.name
    println("Hello ${name}!")
}
