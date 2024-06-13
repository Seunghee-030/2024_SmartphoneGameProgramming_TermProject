package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.paused;

import kr.ac.tukorea.ge.spgp2024.framework.objects.Button;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
import kr.ac.tukorea.ge.spgp2024.sweetdrops.R;

public class PausedScene extends Scene {
    public enum Layer {
        bg, title, touch, COUNT
    }

    public PausedScene() {
        initLayers(Layer.COUNT);
        float w = Metrics.width, h = Metrics.height;
        add(Layer.bg, new Sprite(R.mipmap.drawing_canvas_crop, w / 2, h / 2, w, h));
        add(Layer.bg, new Sprite(R.mipmap.sweet_drops_title_new, w / 2, h / 2, 3.69f, 1.36f));

        add(Layer.touch, new Button(R.mipmap.btn_start, 14.5f, 1.0f, 2.0f, 0.75f, new Button.Callback() {
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
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }
}
