package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.paused;

import kr.ac.tukorea.ge.spgp2024.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.app.MainActivity;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main.MainScene;


public class PausedScene extends Scene {
    public enum Layer {
        bg, title, touch, COUNT
    }

    float rate = 603/444f;
    float rate2 = 170/358f;
    public PausedScene() {
        initLayers(Layer.COUNT);
        float w = Metrics.width, h = Metrics.height;
        add(Layer.bg, new Sprite(R.mipmap.drawing_canvas_crop, w / 2, h / 2, w, w*rate));
        add(Layer.bg, new Sprite(R.mipmap.sweet_drops_title_new, w / 2, h / 2-2f, 6f, 6f*rate2));

        add(Layer.touch, new Button(R.mipmap.btn_start, 3f- 0.5f, 10.0f, 2f,2f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    // 버튼 눌림 동작 처리
                    return true;
                } else if (action == Button.Action.released) {
                    // 버튼 놓임 동작 처리
                    pop();  // 씬을 닫음
                    return true;
                }
                return false;
            }
        }));


        add(Layer.touch, new Button(R.mipmap.btn_restart, 5f- 0.5f, 10.0f, 2f,2f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    // 버튼 눌림 동작 처리
                    return true;
                } else if (action == Button.Action.released) {
                    // 버튼 놓임 동작 처리
                    pop();
                    new MainScene(1).push();
                    return true;
                }
                return false;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.btn_menu, 7f - 0.5f, 10.0f, 2f,2f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if (action == Button.Action.pressed) {
                    // 버튼 눌림 동작 처리
                    return true;
                } else if (action == Button.Action.released) {
                    // 버튼 놓임 동작 처리
                    popAll();
                    return true;
                }
                return false;
            }
        }));

    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
