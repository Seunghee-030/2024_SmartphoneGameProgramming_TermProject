package kr.ac.tukorea.ge.spgp2024.sweetdrops.game.scene.main;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 multiply(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public Vector2 normalize() {
        float magnitude = (float) Math.sqrt(x * x + y * y);
        if (magnitude == 0) {
            return new Vector2(0, 0); // Avoid division by zero
        }
        return new Vector2(x / magnitude, y / magnitude);
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }

    // 중력 상수 정의
    public static final Vector2 GRAVITY = new Vector2(0.0f, -9.8f);

    public static void main(String[] args) {
      /*  // 테스트 출력
        System.out.println(GRAVITY);

        // Test normalization
        Vector2 vec = new Vector2(3, 4);
        System.out.println("Original: " + vec);
        System.out.println("Normalized: " + vec.normalize());*/
    }
}
