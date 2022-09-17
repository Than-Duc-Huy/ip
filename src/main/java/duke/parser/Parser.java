package duke.parser;

import duke.command.*;
import duke.data.Messages;
import duke.data.exceptions.*;
import duke.data.task.*;
import duke.data.TaskList;

public class Parser {

    public Parser() {

    }

    public Command parseCommand(String userInput) {
        String[] parsedInput = userInput.split(" ");
        if (parsedInput.length == 0) {
            return new IncorrectCommand(Messages.UNKNOWN_COMMAND);
        }

        final String commandWord = parsedInput[0];
        final String arguments = userInput.replaceFirst(commandWord, "").trim();

        switch (commandWord) {

        case TodoCommand.COMMAND_NAME:
            return parseTodoCommand(arguments);
        case DeadlineCommand.COMMAND_NAME:
            return parseDeadlineCommand(arguments);
        case EventCommand.COMMAND_NAME:
            return parseEventCommand(arguments);
        case MarkCommand.COMMAND_NAME:
            return parseMarkCommand(arguments);
        case UnmarkCommand.COMMAND_NAME:
            return parseUnmarkCommand(arguments);
        case DeleteCommand.COMMAND_NAME:
            return parseDeleteCommand(arguments);
        case ListCommand.COMMAND_NAME:
            return new ListCommand();
        case ExitCommand.COMMAND_NAME:
        default:
            return new IncorrectCommand(Messages.UNKNOWN_COMMAND);
        }
    }

    public void parseFile(String fileLine) throws DukeFileException {
        String[] parsedLine = fileLine.split(Task.PARSE_LIMITER);
        final String taskType = parsedLine[0];
        switch (taskType) {
        case Todo.TYPE_TODO:
            parseTodoLine(parsedLine);
        case Deadline.TYPE_DEADLINE:
            parseDeadlineLine(parsedLine);
        case Event.TYPE_EVENT:
            parseEventLine(parsedLine);
        default:
            throw new DukeFileException();
        }

    }

    /* Parse Data Line */
    private void parseTodoLine(String[] parsed) throws DukeFileException {

        if (!(parsed.length == 2)) {
            throw new DukeFileException();
        }
        TaskList.list.add(new Todo(parsed[1]));
    }

    private void parseDeadlineLine(String[] parsed) throws DukeFileException {
        if (!(parsed.length == 3)) {
            throw new DukeFileException();
        }
        TaskList.list.add(new Deadline(parsed[1],parsed[2]));
    }

    private void parseEventLine(String[] parsed) throws DukeFileException{
        if (!(parsed.length == 3)) {
            throw new DukeFileException();
        }
        TaskList.list.add(new Event(parsed[1],parsed[2]));
    }

    /* Parse Command */
    private Command parseTodoCommand(String arguments) {
        try {
            String[] parsed = arguments.split("/");
            if (!(parsed.length == 1)) {
                throw new DukeException();
            }
            return new TodoCommand(arguments);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | DukeException e) {
            return new IncorrectCommand(TodoCommand.SYNTAX);
        }

    }

    private Command parseDeadlineCommand(String arguments) {
        try {
            String[] parsed = arguments.split("/");
            if (!(parsed.length == 2)) {
                throw new DukeException();
            }
            return new DeadlineCommand(parsed[0], parsed[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | DukeException e) {

            return new IncorrectCommand(DeadlineCommand.SYNTAX);
        }
    }

    private Command parseEventCommand(String arguments) {
        try {
            String[] parsed = arguments.split("/");
            if (!(parsed.length == 2)) {
                throw new DukeException();
            }
            return new EventCommand(parsed[0], parsed[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | DukeException e) {

            return new IncorrectCommand(EventCommand.SYNTAX);
        }
    }

    private Command parseMarkCommand(String arguments) {
        try {
            String[] parsed = arguments.split(" ");
            if (parsed.length < 1) {
                throw new DukeException();
            }
            return new MarkCommand(strToIntArray(parsed));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | DukeException e) {

            return new IncorrectCommand(MarkCommand.SYNTAX);
        }
    }

    
    private Command parseUnmarkCommand(String arguments) {
        try {
            String[] parsed = arguments.split(" ");
            if (parsed.length < 1) {
                throw new DukeException();
            }
            return new UnmarkCommand(strToIntArray(parsed));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | DukeException e) {

            return new IncorrectCommand(UnmarkCommand.SYNTAX);
        }
    }

    private Command parseDeleteCommand(String arguments) {
        try {
            String[] parsed = arguments.split(" ");
            if (parsed.length < 1) {
                throw new DukeException();
            }
            return new DeleteCommand(strToIntArray(parsed));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | DukeException e) {
            return new IncorrectCommand(DeleteCommand.SYNTAX);
        }
    }
    
    private Integer[] strToIntArray(String[] parsed) {
        Integer[] intParsed ={};
        for(int i = 0; i < parsed.length;i++){
            intParsed[i] = Integer.parseInt(parsed[i]);
        }
        return intParsed;
    }
}
