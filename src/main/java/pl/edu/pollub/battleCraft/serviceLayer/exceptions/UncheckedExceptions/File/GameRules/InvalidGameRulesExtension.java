package pl.edu.pollub.battleCraft.serviceLayer.exceptions.UncheckedExceptions.File.GameRules;

public class InvalidGameRulesExtension extends RuntimeException {
    public InvalidGameRulesExtension(String extension) {
        super(new StringBuilder("Extension: ").append(extension).append(" is not acceptable extension of user avatar. You should try with jpg, gif, bmp or png").toString());
    }
}
