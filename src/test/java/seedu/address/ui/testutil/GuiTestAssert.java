package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;

import guitests.guihandles.MemberCardHandle;
import guitests.guihandles.MemberListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StaffCardHandle;
import seedu.address.model.person.Member;
import seedu.address.model.person.Staff;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(MemberCardHandle expectedCard, MemberCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(StaffCardHandle expectedCard, StaffCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getAppointment(), actualCard.getAppointment());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMember}.
     */
    public static void assertCardDisplaysMember(Member expectedMember, MemberCardHandle actualCard) {
        assertEquals(expectedMember.getName().fullName, actualCard.getName());
        assertEquals(expectedMember.getPhone().value, actualCard.getPhone());
        assertEquals(expectedMember.getEmail().value, actualCard.getEmail());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedStaff}.
     */
    public static void assertCardDisplaysStaff(Staff expectedStaff, StaffCardHandle actualCard) {
        assertEquals(expectedStaff.getName().fullName, actualCard.getName());
        assertEquals(expectedStaff.getPhone().value, actualCard.getPhone());
        assertEquals(expectedStaff.getEmail().value, actualCard.getEmail());
        assertEquals(expectedStaff.getAppointment().appointmentName, actualCard.getAppointment());
    }

    /**
     * Asserts that the list in {@code memberListPanelHandle} displays the details of {@code members} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MemberListPanelHandle memberListPanelHandle, Member... members) {
        for (int i = 0; i < members.length; i++) {
            memberListPanelHandle.navigateToCard(i);
            assertCardDisplaysMember(members[i], memberListPanelHandle.getMemberCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code memberListPanelHandle} displays the details of {@code members} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MemberListPanelHandle memberListPanelHandle, List<Member> members) {
        assertListMatching(memberListPanelHandle, members.toArray(new Member[0]));
    }

    /**
     * Asserts the size of the list in {@code memberListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(MemberListPanelHandle memberListPanelHandle, int size) {
        int numberOfPeople = memberListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
