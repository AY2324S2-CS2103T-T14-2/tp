package seedu.address.logic.parser;

/**
 * A flag that modifies the behaviour of a command, represented as a prefix with no arguments.
 * E.g. '-a' in 'list -a'
 */
public class Flag extends Prefix {
    public Flag(String flag) {
        super(flag);
    }
}
