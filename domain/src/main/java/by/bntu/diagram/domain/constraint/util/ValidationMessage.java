package by.bntu.diagram.domain.constraint.util;

public final class ValidationMessage {

    private ValidationMessage() {
    }

    public static final class Diagram {
        public static final String UUID_NULL = "{diagram.uuid.null}";

        public static final String NAME_BLANK = "{diagram.name.blank}";
        public static final String NAME_SIZE = "{diagram.name.size}";
        public static final String DESCRIPTION_BLANK = "{diagram.description.blank}";
        public static final String DESCRIPTION_SIZE = "{diagram.description.size}";
        public static final String STATES_NULL = "{diagram.states.null}";

        private Diagram() {
        }
    }

    public static final class State {

        public static final String UUID_NULL = "{state.uuid.null}";

        public static final String POS_X_NULL = "{state.position-x.null}";
        public static final String POS_Y_NULL = "{state.position-y.null}";

        public static final String TEMPLATE_BLANK = "{state.template.blank}";
        public static final String TEMPLATE_SIZE = "{state.template.size}";

        public static final String NAME_BLANK = "{state.name.blank}";
        public static final String NAME_SIZE = "{state.name.size}";

        public static final String COLOR_BLANK = "{state.color.blank}";
        public static final String COLOR_SIZE = "{state.color.size}";

        public static final String TARGETS_NULL = "{state.targets.null}";
        public static final String SOURCES_NULL = "{state.sources.null}";
        public static final String CONNECTIONS_NULL = "{state.connections.null}";

        public static final String STYLE_NULL = "{state.style.null}";

        public static final String IC_NULL = "{state.input-container.null}";
        public static final String OC_NULL = "{state.output-container.null}";

        private State() {

        }
    }

    public static final class Source {

        public static final String UUID_NULL = "{source.uuid.null}";

        private Source() {

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

    public static final class Target {

        public static final String UUID_NULL = "{target.uuid.null}";

        private Target() {
        }
    }

    public static final class Style {


        public static final String SOURCE_ANCHOR_STYLE_BLANK = "{style.source-anchor-style.blank}";
        public static final String SOURCE_ANCHOR_STYLE_SIZE = "{style.source-anchor-style.size}";

        public static final String SOURCE_STYLE_BLANK = "{style.source-style.blank}";
        public static final String SOURCE_STYLE_SIZE = "{style.source-style.size}";

        public static final String TARGET_ANCHOR_STYLE_BLANK = "{style.target-anchor-style.blank}";
        public static final String TARGET_ANCHOR_STYLE_SIZE = "{style.target-anchor-style.size}";

        public static final String TARGET_STYLE_BLANK = "{style.target-style.blank}";
        public static final String TARGET_STYLE_SIZE = "{style.target-style.size}";

        private Style() {

        }
    }


    public static final class Connection {

        public static final String TARGET_NULL = "{connection.target.null}";
        public static final String SOURCE_NULL = "{connection.source.null}";

        private Connection() {

        }
    }
}
