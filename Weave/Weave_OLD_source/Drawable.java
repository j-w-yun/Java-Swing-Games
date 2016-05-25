

// having the Display object held by Drawable reference variable in GameState allows us to put literally zero effort into implementing android API with GameState
interface Drawable
{
	// implement with repaint() on swing API or postinvalidate() on android API
	void draw();
}