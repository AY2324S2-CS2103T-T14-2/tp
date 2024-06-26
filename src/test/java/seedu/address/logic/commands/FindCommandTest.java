package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.ELLE;
import static seedu.address.testutil.TypicalPatients.FIONA;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.PhoneMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        PhoneMatchesPredicate thirdPredicate =
                new PhoneMatchesPredicate(new Phone("99999999"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate, PREDICATE_SHOW_ALL_PATIENTS);
        FindCommand findSecondCommand = new FindCommand(secondPredicate, PREDICATE_SHOW_ALL_PATIENTS);
        FindCommand findThirdCommand = new FindCommand(thirdPredicate, PREDICATE_SHOW_ALL_PATIENTS);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, PREDICATE_SHOW_ALL_PATIENTS);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different name -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findFirstCommand.equals(findThirdCommand));
    }

    @Test
    public void execute_zeroKeywords_noPatientFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(namePredicate, PREDICATE_SHOW_ALL_PATIENTS);
        expectedModel.updateFilteredPatientList(namePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPatientList());
    }

    @Test
    public void execute_multipleKeywords_multiplePatientsFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(namePredicate, PREDICATE_SHOW_ALL_PATIENTS);
        expectedModel.updateFilteredPatientList(namePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPatientList());
    }

    @Test
    public void execute_phoneNotFound_noPatientFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 0);
        PhoneMatchesPredicate phoneMatchesPredicate = preparePhonePredicate("999");
        FindCommand command = new FindCommand(PREDICATE_SHOW_ALL_PATIENTS, phoneMatchesPredicate);
        expectedModel.updateFilteredPatientList(phoneMatchesPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPatientList());
    }

    @Test
    public void execute_phonePredicate_onePatientFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 1);
        PhoneMatchesPredicate phoneMatchesPredicate = preparePhonePredicate("94351253");
        FindCommand command = new FindCommand(PREDICATE_SHOW_ALL_PATIENTS, phoneMatchesPredicate);
        expectedModel.updateFilteredPatientList(phoneMatchesPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPatientList());
    }

    @Test
    public void execute_nameAndPhonePredicate_onePatientFound() {
        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 1);
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("Alice");
        PhoneMatchesPredicate phoneMatchesPredicate = preparePhonePredicate("94351253");
        FindCommand command = new FindCommand(namePredicate, phoneMatchesPredicate);
        expectedModel.updateFilteredPatientList(namePredicate.and(phoneMatchesPredicate));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPatientList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate namePredicate = prepareNamePredicate("name");
        PhoneMatchesPredicate phonePredicate = preparePhonePredicate("12345678");
        FindCommand findCommand = new FindCommand(namePredicate, phonePredicate);
        String expected = FindCommand.class.getCanonicalName() + "{namePredicate=" + namePredicate + ", "
                + "phonePredicate=" + phonePredicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneMatchesPredicate}.
     */
    private PhoneMatchesPredicate preparePhonePredicate(String userInput) {
        return new PhoneMatchesPredicate(new Phone(userInput));
    }
}
