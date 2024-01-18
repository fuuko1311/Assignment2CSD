import java.util.Stack;

// Custom class to represent a text edit operation
class TextEditOperation {
    enum OperationType {
        INSERT, DELETE
    }

    OperationType type;
    String text;

    TextEditOperation(OperationType type, String text) {
        this.type = type;
        this.text = text;
    }
}

// TextEditor class with undo functionality
public class TextEditor {
    private StringBuilder content;
    private Stack<TextEditOperation> undoStack;

    public TextEditor() {
        content = new StringBuilder();
        undoStack = new Stack<>();
    }

    public void insertText(String text) {
        content.append(text);
        undoStack.push(new TextEditOperation(TextEditOperation.OperationType.DELETE, text));
    }

    public void deleteLastCharacter() {
        if (content.length() > 0) {
            char lastChar = content.charAt(content.length() - 1);
            content.deleteCharAt(content.length() - 1);
            undoStack.push(new TextEditOperation(TextEditOperation.OperationType.INSERT, String.valueOf(lastChar)));
        }
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            TextEditOperation lastOperation = undoStack.pop();
            if (lastOperation.type == TextEditOperation.OperationType.INSERT) {
                content.append(lastOperation.text);
            } else if (lastOperation.type == TextEditOperation.OperationType.DELETE) {
                int length = lastOperation.text.length();
                content.delete(content.length() - length, content.length());
            }
        }
    }

    public String getContent() {
        return content.toString();
    }

    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();

        // Perform some text modifications
        textEditor.insertText("Hello ");
        System.out.println("Content: " + textEditor.getContent());

        textEditor.insertText("World");
        System.out.println("Content: " + textEditor.getContent());

        textEditor.deleteLastCharacter();
        System.out.println("Content: " + textEditor.getContent());

        // Undo the last operation
        textEditor.undo();
        System.out.println("Content after undo: " + textEditor.getContent());
    }
}
