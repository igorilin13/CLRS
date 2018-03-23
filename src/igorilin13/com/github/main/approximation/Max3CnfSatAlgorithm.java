package igorilin13.com.github.main.approximation;

import igorilin13.com.github.main.util.StringUtils;

import java.util.*;

public class Max3CnfSatAlgorithm {
    private static final String VARIABLE_PREFIX = "x";
    private static final int CLAUSE_SIZE = 3;

    private final List<Clause> formula;
    private final Set<Integer> variableIds;

    private final Map<Integer, Boolean> resultAssignment;
    private final int numberOfTrueClauses;

    /**
     * Example of valid string:
     * "(x1 + !x2 + x4)(!x1 + x3 + !x5)(x2 + !x4 + x6)"
     */
    public Max3CnfSatAlgorithm(String formulaString) {
        FormulaParser parser = new FormulaParser(formulaString);
        formula = parser.getParsedFormula();
        variableIds = parser.getVariableIds();

        resultAssignment = findAssignment();
        numberOfTrueClauses = evaluate();
    }

    private Map<Integer, Boolean> findAssignment() {
        Random random = new Random();
        Map<Integer, Boolean> result = new HashMap<>();
        for (int varId : variableIds) {
            result.put(varId, random.nextBoolean());
        }
        return result;
    }

    private int evaluate() {
        int res = 0;
        for (Clause clause : formula) {
            for (Variable variable : clause.variables) {
                boolean assignedValue = resultAssignment.get(variable.id);
                if (assignedValue != variable.isNegated) {
                    res++;
                    break;
                }
            }
        }
        return res;
    }

    public int getNumberOfTrueClauses() {
        return numberOfTrueClauses;
    }

    private static class Variable {
        private final int id;
        private final boolean isNegated;

        private Variable(int id, boolean isNegated) {
            this.id = id;
            this.isNegated = isNegated;
        }

        @Override
        public String toString() {
            return (isNegated ? "!" : "") + VARIABLE_PREFIX + id;
        }
    }

    private static class Clause {
        private final List<Variable> variables;

        private Clause(List<Variable> variables) {
            if (variables.size() != CLAUSE_SIZE) {
                throw new IllegalArgumentException("Unexpected clause size: " + variables.size());
            }
            this.variables = variables;
        }
    }

    private static class FormulaParser {
        private final String formula;
        private final List<Clause> parsedFormula;
        private final Set<Integer> variableIds;
        private String formulaCopy;

        private FormulaParser(String formula) {
            this.formula = formulaCopy = formula;
            parsedFormula = new ArrayList<>();
            variableIds = new HashSet<>();
            parse();
        }

        private void parse() {
            removeSpaces();
            trimParenthesis();

            String[] clauses = formulaCopy.split("\\)\\(");
            for (String clause : clauses) {
                parsedFormula.add(parseClause(clause));
            }
        }

        private void removeSpaces() {
            formulaCopy = formulaCopy.replaceAll("\\s", "");
        }

        private void trimParenthesis() {
            int indexOfFirst = StringUtils.indexOfRegex(formula, ".*?\\(");
            int indexOfLast = formulaCopy.lastIndexOf(")");
            formulaCopy = formulaCopy.substring(indexOfFirst + 1, indexOfLast);
        }

        private Clause parseClause(String clause) {
            String[] variables = clause.split("\\+");
            List<Variable> clauseVariables = new ArrayList<>();
            for (String variable : variables) {
                boolean isNegated = variable.startsWith("!");
                int id = Integer.parseInt(variable.substring(variable.indexOf(VARIABLE_PREFIX) + 1));
                clauseVariables.add(new Variable(id, isNegated));
                variableIds.add(id);
            }
            return new Clause(clauseVariables);
        }

        private List<Clause> getParsedFormula() {
            return parsedFormula;
        }

        private Set<Integer> getVariableIds() {
            return variableIds;
        }
    }
}
