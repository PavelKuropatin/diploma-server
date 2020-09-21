package by.bntu.constructor.domain.constraint.util;

public final class ValidationMessage {

    private ValidationMessage() {
    }

    public static final class Schema {
        public static final String UUID_NULL = "{schema.uuid.null}";

        public static final String NAME_BLANK = "{schema.name.blank}";
        public static final String NAME_SIZE = "{schema.name.size}";
        public static final String DESCRIPTION_BLANK = "{schema.description.blank}";
        public static final String DESCRIPTION_SIZE = "{schema.description.size}";
        public static final String BLOCKS_NULL = "{schema.blocks.null}";

        private Schema() {
        }
    }

    public static final class Block {

        public static final String UUID_NULL = "{block.uuid.null}";

        public static final String POS_X_NULL = "{block.position-x.null}";
        public static final String POS_Y_NULL = "{block.position-y.null}";

        public static final String TEMPLATE_BLANK = "{block.template.blank}";
        public static final String TEMPLATE_SIZE = "{block.template.size}";

        public static final String NAME_BLANK = "{block.name.blank}";
        public static final String NAME_SIZE = "{block.name.size}";

        public static final String COLOR_BLANK = "{block.color.blank}";
        public static final String COLOR_SIZE = "{block.color.size}";

        public static final String OUTPUTS_NULL = "{block.outputs.null}";
        public static final String INPUTS_NULL = "{block.inputs.null}";
        public static final String CONNECTIONS_NULL = "{block.connections.null}";

        public static final String STYLE_NULL = "{block.style.null}";

        public static final String INPUT_VARS_NULL = "{block.input-vars.null}";
        public static final String OUTPUT_VARS_NULL = "{block.output-vars.null}";

        private Block() {

        }
    }

    public static final class Input {

        public static final String UUID_NULL = "{input.uuid.null}";

        private Input() {

        }
    }

    public static final class Variable {

        public static final String TYPE_NULL = "{variable.type.null}";
        public static final String PARAM_BLANK = "{variable.param.blank}";
        public static final String PARAM_SIZE = "{variable.param.size}";
        public static final String VALUE_NULL = "{variable.value.null}";
        public static final String FUNC_NULL_OR_SIZE = "{variable.function.null-or-size}";

        private Variable() {

        }
    }

    public static final class Output {

        public static final String UUID_NULL = "{output.uuid.null}";

        private Output() {
        }
    }

    public static final class Style {


        public static final String INPUT_ANCHOR_STYLE_BLANK = "{style.input-anchor-style.blank}";
        public static final String INPUT_ANCHOR_STYLE_SIZE = "{style.input-anchor-style.size}";

        public static final String INPUT_STYLE_BLANK = "{style.input-style.blank}";
        public static final String INPUT_STYLE_SIZE = "{style.input-style.size}";

        public static final String OUTPUT_ANCHOR_STYLE_BLANK = "{style.output-anchor-style.blank}";
        public static final String OUTPUT_ANCHOR_STYLE_SIZE = "{style.output-anchor-style.size}";

        public static final String OUTPUT_STYLE_BLANK = "{style.output-style.blank}";
        public static final String OUTPUT_STYLE_SIZE = "{style.output-style.size}";

        private Style() {

        }
    }


    public static final class Connection {

        public static final String OUTPUT_NULL = "{connection.output.null}";
        public static final String INPUT_NULL = "{connection.input.null}";

        private Connection() {

        }
    }
}
