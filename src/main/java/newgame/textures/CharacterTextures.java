package newgame.textures;

import com.badlogic.gdx.graphics.Texture;

import newgame.logger.GameEventsLogger;
import newgame.logger.LogMessages;

/** Class to load the character textures
 *
 * @author Maxim Fründt
 */
public enum CharacterTextures
{
    // HERO TEXTURES

    // Hero male idle to the right
    HERO_M_IDLE_R_0("hero/knight_m_idle_r_anim_f0.png"),
    HERO_M_IDLE_R_1("hero/knight_m_idle_r_anim_f1.png"),
    HERO_M_IDLE_R_2("hero/knight_m_idle_r_anim_f2.png"),
    HERO_M_IDLE_R_3("hero/knight_m_idle_r_anim_f3.png"),

    // Hero male idle to the left
    HERO_M_IDLE_L_0("hero/knight_m_idle_l_anim_f0.png"),
    HERO_M_IDLE_L_1("hero/knight_m_idle_l_anim_f1.png"),
    HERO_M_IDLE_L_2("hero/knight_m_idle_l_anim_f2.png"),
    HERO_M_IDLE_L_3("hero/knight_m_idle_l_anim_f3.png"),

    // Hero male run left
    HERO_M_RUN_L_0("hero/knight_m_run_l_anim_f0.png"),
    HERO_M_RUN_L_1("hero/knight_m_run_l_anim_f1.png"),
    HERO_M_RUN_L_2("hero/knight_m_run_l_anim_f2.png"),
    HERO_M_RUN_L_3("hero/knight_m_run_l_anim_f3.png"),

    // Hero male run right
    HERO_M_RUN_R_0("hero/knight_m_run_r_anim_f0.png"),
    HERO_M_RUN_R_1("hero/knight_m_run_r_anim_f1.png"),
    HERO_M_RUN_R_2("hero/knight_m_run_r_anim_f2.png"),
    HERO_M_RUN_R_3("hero/knight_m_run_r_anim_f3.png"),

    // Hero hit
    HERO_M_HIT_R("hero/knight_m_hit_r_anim_f0.png"),
    HERO_M_HIT_L("hero/knight_m_hit_l_anim_f0.png"),

    // ZOMBIE TEXTURES

    // Zombie idle to the left
    ZOMBIE_IDLE_L_0("monsters/zombie_idle_anim_l_f0.png"),
    ZOMBIE_IDLE_L_1("monsters/zombie_idle_anim_l_f1.png"),
    ZOMBIE_IDLE_L_2("monsters/zombie_idle_anim_l_f2.png"),
    ZOMBIE_IDLE_L_3("monsters/zombie_idle_anim_l_f3.png"),

    // Zombie idle to the right
    ZOMBIE_IDLE_R_0("monsters/zombie_idle_anim_r_f0.png"),
    ZOMBIE_IDLE_R_1("monsters/zombie_idle_anim_r_f1.png"),
    ZOMBIE_IDLE_R_2("monsters/zombie_idle_anim_r_f2.png"),
    ZOMBIE_IDLE_R_3("monsters/zombie_idle_anim_r_f3.png"),

    // Zombie run left
    ZOMBIE_RUN_L_0("monsters/zombie_run_anim_l_f0.png"),
    ZOMBIE_RUN_L_1("monsters/zombie_run_anim_l_f1.png"),
    ZOMBIE_RUN_L_2("monsters/zombie_run_anim_l_f2.png"),
    ZOMBIE_RUN_L_3("monsters/zombie_run_anim_l_f3.png"),

    // Zombie run right
    ZOMBIE_RUN_R_0("monsters/zombie_run_anim_r_f0.png"),
    ZOMBIE_RUN_R_1("monsters/zombie_run_anim_r_f1.png"),
    ZOMBIE_RUN_R_2("monsters/zombie_run_anim_r_f2.png"),
    ZOMBIE_RUN_R_3("monsters/zombie_run_anim_r_f3.png"),

    // SKELETON TEXTURES

    // Skeleton idle to the left
    SKELETON_IDLE_L_0("monsters/skeleton_idle_anim_l_f0.png"),
    SKELETON_IDLE_L_1("monsters/skeleton_idle_anim_l_f1.png"),
    SKELETON_IDLE_L_2("monsters/skeleton_idle_anim_l_f2.png"),
    SKELETON_IDLE_L_3("monsters/skeleton_idle_anim_l_f3.png"),

    // Skeleton idle to the right
    SKELETON_IDLE_R_0("monsters/skeleton_idle_anim_r_f0.png"),
    SKELETON_IDLE_R_1("monsters/skeleton_idle_anim_r_f1.png"),
    SKELETON_IDLE_R_2("monsters/skeleton_idle_anim_r_f2.png"),
    SKELETON_IDLE_R_3("monsters/skeleton_idle_anim_r_f3.png"),

    // Skeleton run left
    SKELETON_RUN_L_0("monsters/skeleton_run_anim_l_f0.png"),
    SKELETON_RUN_L_1("monsters/skeleton_run_anim_l_f1.png"),
    SKELETON_RUN_L_2("monsters/skeleton_run_anim_l_f2.png"),
    SKELETON_RUN_L_3("monsters/skeleton_run_anim_l_f3.png"),

    // Skeleton run right
    SKELETON_RUN_R_0("monsters/skeleton_run_anim_r_f0.png"),
    SKELETON_RUN_R_1("monsters/skeleton_run_anim_r_f1.png"),
    SKELETON_RUN_R_2("monsters/skeleton_run_anim_r_f2.png"),
    SKELETON_RUN_R_3("monsters/skeleton_run_anim_r_f3.png"),

    // DEMON TEXTURES

    // Demon idle to the left
    DEMON_IDLE_L_0("monsters/big_demon_idle_anim_l_f0.png"),
    DEMON_IDLE_L_1("monsters/big_demon_idle_anim_l_f1.png"),
    DEMON_IDLE_L_2("monsters/big_demon_idle_anim_l_f2.png"),
    DEMON_IDLE_L_3("monsters/big_demon_idle_anim_l_f3.png"),

    // Demon idle to the right
    DEMON_IDLE_R_0("monsters/big_demon_idle_anim_r_f0.png"),
    DEMON_IDLE_R_1("monsters/big_demon_idle_anim_r_f1.png"),
    DEMON_IDLE_R_2("monsters/big_demon_idle_anim_r_f2.png"),
    DEMON_IDLE_R_3("monsters/big_demon_idle_anim_r_f3.png"),

    // Demon run to the left
    DEMON_RUN_L_0("monsters/big_demon_run_anim_l_f0.png"),
    DEMON_RUN_L_1("monsters/big_demon_run_anim_l_f1.png"),
    DEMON_RUN_L_2("monsters/big_demon_run_anim_l_f2.png"),
    DEMON_RUN_L_3("monsters/big_demon_run_anim_l_f3.png"),

    // Demon run to the right
    DEMON_RUN_R_0("monsters/big_demon_run_anim_r_f0.png"),
    DEMON_RUN_R_1("monsters/big_demon_run_anim_r_f1.png"),
    DEMON_RUN_R_2("monsters/big_demon_run_anim_r_f2.png"),
    DEMON_RUN_R_3("monsters/big_demon_run_anim_r_f3.png"),

    // Wizard textures

    // Wizard idle
    WIZARD_IDLE_0("friendly_npc/wizard_m_idle_anim_f0.png"),
    WIZARD_IDLE_1("friendly_npc/wizard_m_idle_anim_f1.png"),
    WIZARD_IDLE_2("friendly_npc/wizard_m_idle_anim_f2.png"),
    WIZARD_IDLE_3("friendly_npc/wizard_m_idle_anim_f3.png");

    /* Path of the folder of all character textures */
    private static final String characterAssetsPath = "assets/textures/characters/";

    /* Input name of the file that will be loaded */
    private final String fileName;

    /* Create a new texture within given const fileName */
    CharacterTextures(String fileName)
    {
        this.fileName = fileName;
    }

    /** Get the texture with the input file name
     *
     * @return Loaded texture
     */
    public Texture getTexture()
    {
        if (fileName != null)
        {
            return new Texture(characterAssetsPath + fileName);
        }
        else
        {
            GameEventsLogger.getLogger().severe(LogMessages.NO_TEXTURE_FOUND.toString());
            throw new IllegalArgumentException(LogMessages.NO_TEXTURE_FOUND.toString());
        }
    }
}